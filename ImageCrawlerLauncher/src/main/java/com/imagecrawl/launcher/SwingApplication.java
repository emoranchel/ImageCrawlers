package com.imagecrawl.launcher;

import com.imagecrawl.api.API;
import com.imagecrawl.crawlerswingview.Closing;
import com.imagecrawl.crawlerswingview.MainWindow;
import com.imagecrawl.services.Analizer;
import com.imagecrawl.tasks.HttpHandler;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.asmatron.messengine.engines.DefaultEngine;
import org.asmatron.messengine.engines.Engine;
import org.asmatron.messengine.engines.support.EngineConfigurator;

public class SwingApplication extends BaseApp {

  private Engine engine;

  private SwingApplication(String... args) {
    super(args);
  }

  @Override
  public void init() {
    engine = new DefaultEngine();
  }

  @Override
  public void start() throws Exception {
    EngineConfigurator configurator = new EngineConfigurator(engine);

    Analizer analizer = new Analizer(engine, engine);
    MainWindow mainWindow = new MainWindow(engine);
    engine.set(API.Model.HTTP_HANDLER, new HttpHandler() {

      @Override
      public HttpResponse getDownload(String string) throws Exception {
        return get(string);
      }

      @Override
      public HttpResponse get(String string) throws Exception {
        System.out.println("REQUEST::" + string);
        int retry = 0;
        HttpResponse response = null;
        Exception ex = null;
        do {
          try {
            HttpClient httpClient = new DefaultHttpClient();
            response = httpClient.execute(new HttpGet(string));
            if (response.getStatusLine().getStatusCode() == 200) {
              return response;
            } else {
              throw new Exception("Response returned: " + response.getStatusLine());
            }
          } catch (Exception e) {
            ex = e;
            retry++;
            Thread.sleep(10000);

          }
        } while (retry < 3);
        if (response != null) {
          return response;
        }
        throw ex == null ? new RuntimeException() : ex;
      }
    }, null);

    setupFactory(engine);

    configurator.setup(analizer, mainWindow);
    final Semaphore lock = new Semaphore(0);

    mainWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        engine.stop(() -> {
          lock.release();
        });
        e.getWindow().dispose();
      }
    });

    mainWindow.setVisible(true);

    engine.start();
    try {
      lock.acquire();
    } catch (InterruptedException ex) {
      Logger.getLogger(SwingApplication.class.getName()).log(Level.SEVERE, null, ex);
    }

    Closing closingWindow = new Closing();
    closingWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
      }
    });
    closingWindow.setVisible(true);
    try {
      analizer.awaitTermination(1, TimeUnit.HOURS);
    } catch (InterruptedException ex) {
      Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
    Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new WindowEvent(closingWindow, WindowEvent.WINDOW_CLOSING));
  }

  @Override
  public void stop() {
    //safety call... just in case the app does not close correctly
    System.exit(0);
  }

  public static void main(String[] args) throws Exception {
    SwingApplication application = new SwingApplication(args);
    application.init();
    application.start();
    application.stop();
  }
}
