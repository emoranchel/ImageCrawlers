package com.imagecrawl.launcher;

import com.imagecrawl.engine.XtendedEngineConfigurator;
import com.imagecrawl.fx.FxApplication;
import com.imagecrawl.fx.MainWindowController;
import com.imagecrawl.services.Analizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asmatron.messengine.ViewEngine;
import org.asmatron.messengine.engines.Engine;

class FxView implements View {

  private final MainWindowController controller;

  public FxView(ViewEngine engine) {
    this.controller = new MainWindowController(engine);
  }

  @Override
  public void setup(XtendedEngineConfigurator configurator) {
    configurator.setup(controller);
  }

  @Override
  public void start(final Engine engine, Analizer analizer) {
    try {
      FxApplication.go(controller);
    } catch (Exception ex) {
      Logger.getLogger(FxView.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void onEngineStart() {
  }

  @Override
  public void onEngineStop() {
  }
}
