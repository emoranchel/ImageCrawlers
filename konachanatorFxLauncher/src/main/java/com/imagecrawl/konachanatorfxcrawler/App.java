package com.imagecrawl.konachanatorfxcrawler;

import com.imagecrawl.api.API;
import com.imagecrawl.crawlerswingview.Closing;
import com.imagecrawl.crawlerswingview.MainWindow;
import com.imagecrawl.engine.XtendedEngine;
import com.imagecrawl.engine.XtendedEngineConfigurator;
import com.imagecrawl.services.Analizer;
import com.rip.konachan.konachanator.KonachanCrawlerFactory;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

  public static void main(String[] args) throws Exception {
    final XtendedEngine engine = new XtendedEngine();

    MainWindow mainWindow = new MainWindow(engine);
    final Analizer analizer = new Analizer(engine, engine);

    XtendedEngineConfigurator configurator = new XtendedEngineConfigurator(engine);
    configurator.setup(mainWindow, analizer);

    engine.set(API.Model.FACTORY, new KonachanCrawlerFactory(engine, engine), null);
    engine.set(API.Model.TITLE, "Konachan.com", null);

    engine.start();

    mainWindow.setVisible(true);

    mainWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        engine.stop();
        Closing closingWindow = new Closing();
        closingWindow.setVisible(true);
        new Thread(new Runnable() {
          @Override
          public void run() {
            try {
              analizer.awaitTermination(1, TimeUnit.HOURS);
            } catch (InterruptedException ex) {
              Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
          }
        }).start();
      }
    });
  }
}
