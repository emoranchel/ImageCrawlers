package com.imagecrawl.launcher;

import com.imagecrawl.engine.XtendedEngine;
import com.imagecrawl.engine.XtendedEngineConfigurator;
import com.imagecrawl.services.Analizer;

public class ConsoleApplication extends BaseApp {

  private XtendedEngine engine;

  public ConsoleApplication(String[] args) {
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
    ConsoleView view = new ConsoleView(engine);
    setupFactory(engine);
    configurator.setup(view, analizer);
  }

  @Override
  public void stop() {
    engine.stop();
    engine.awaitStop();
  }

  public static void main(String[] args) {
    ConsoleApplication application = new ConsoleApplication(args);
    application.init();
    application.start();
    application.stop();

  }
}
