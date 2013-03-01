/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagecrawl.engine;

import org.asmatron.messengine.engines.support.EngineConfigurator;

/**
 *
 * @author Eduardo
 */
public class XtendedEngineConfigurator extends EngineConfigurator {

  private final EngineListenerConfigurator engineListenerConfigurator;

  public XtendedEngineConfigurator(XtendedEngine engine) {
    super(engine);
    engineListenerConfigurator = new EngineListenerConfigurator(engine);
  }

  @Override
  public void setup(Object bean) {
    super.setup(bean);
    engineListenerConfigurator.setupListeners(bean);
  }

  @Override
  public void reset(Object bean) {
    super.reset(bean);
    engineListenerConfigurator.resetListeners(bean);
  }
}
