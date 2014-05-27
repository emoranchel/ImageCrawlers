package com.imagecrawl.launcher;

import com.imagecrawl.api.API;
import com.imagecrawl.crawlerswingview.Closing;
import com.imagecrawl.crawlerswingview.MainWindow;
import com.imagecrawl.engine.EngineListener;
import com.imagecrawl.engine.XtendedEngine;
import com.imagecrawl.engine.XtendedEngineConfigurator;
import com.imagecrawl.services.Analizer;
import com.imagecrawl.tasks.HttpHandler;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class SwingApplication extends BaseApp implements EngineListener {

  private XtendedEngine engine;

  private SwingApplication(String... args) {
    super(args);
  }

  @Override
  public void init() {
    engine = new XtendedEngine();
  }

  @Override
  public void start() {
    XtendedEngineConfigurator configurator = new XtendedEngineConfigurator(engine);

    Analizer analizer = new Analizer(engine, engine);
    MainWindow mainWindow = new MainWindow(engine);
    engine.set(API.Model.HTTP_HANDLER, new HttpHandler() {

      @Override
      public HttpResponse get(String string) throws Exception {
        int retry = 0;
        HttpResponse response = null;
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
            if (retry < 3) {
              retry++;
              Thread.sleep(10000);
            } else {
              throw e;
            }
          }
        } while (retry < 3);
        return response;
      }
    }, null);

    setupFactory(engine);

    configurator.setup(analizer, mainWindow);
    engine.addEngineListener(this);

    mainWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        engine.stop();
        e.getWindow().dispose();
      }
    });

    mainWindow.setVisible(true);

    engine.start();
    engine.awaitStop();

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

  @Override
  public void onEngineStart() {
  }

  @Override
  public void onEngineStop() {
  }

  public static void main(String[] args) {
    //SwingApplication application = new SwingApplication(args);
    SwingApplication application = new SwingApplication("konachan");
    application.init();
    application.start();
    application.stop();
  }
}
