package com.imagecrawl.launcher;

import com.imagecrawl.services.Analizer;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asmatron.messengine.engines.DefaultEngine;
import org.asmatron.messengine.engines.Engine;
import org.asmatron.messengine.engines.support.EngineConfigurator;

public class ConsoleApplication extends BaseApp {

  private Engine engine;

  public ConsoleApplication(String[] args) {
    super(args);
  }

  @Override
  public void init() {
    engine = new DefaultEngine();
  }

  @Override
  public void start() {
    EngineConfigurator configurator = new EngineConfigurator(engine);
    Analizer analizer = new Analizer(engine, engine);
    ConsoleView view = new ConsoleView();
    setupFactory(engine);
    configurator.setup(view, analizer);
  }

  @Override
  public void stop() {
    final Semaphore lock = new Semaphore(0);
    engine.stop(() -> {
      lock.release();
    });
    try {
      lock.tryAcquire(3, TimeUnit.MINUTES);
    } catch (InterruptedException ex) {
      Logger.getLogger(ConsoleApplication.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static void main(String[] args) {
    ConsoleApplication application = new ConsoleApplication(args);
    application.init();
    application.start();
    application.stop();

  }
}
