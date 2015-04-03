package com.imagecrawl.launcher;

import com.imagecrawl.services.Analizer;
import org.asmatron.messengine.EngineListener;
import org.asmatron.messengine.engines.Engine;
import org.asmatron.messengine.engines.support.EngineConfigurator;

public interface View extends EngineListener {

  public void setup(EngineConfigurator configurator);

  public void start(Engine engine, Analizer analizer);
}
