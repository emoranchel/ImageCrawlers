package com.imagecrawl.launcher;

import com.imagecrawl.engine.EngineListener;
import com.imagecrawl.engine.XtendedEngineConfigurator;
import com.imagecrawl.services.Analizer;
import org.asmatron.messengine.engines.Engine;

public interface View extends EngineListener {

  public void setup(XtendedEngineConfigurator configurator);

  public void start(Engine engine, Analizer analizer);
}
