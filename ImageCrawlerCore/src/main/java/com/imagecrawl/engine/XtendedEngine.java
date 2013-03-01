/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagecrawl.engine;

import java.util.HashSet;
import java.util.Set;
import org.asmatron.messengine.engines.DefaultEngine;

/**
 *
 * @author Eduardo
 */
public class XtendedEngine extends DefaultEngine {

  private final Set<EngineListener> listeners;

  public XtendedEngine() {
    super();
    this.listeners = new HashSet<EngineListener>();

  }

  @Override
  public void start() {
    super.start();
    for (EngineListener listener : listeners) {
      listener.onEngineStart();
    }
  }

  @Override
  public void stop() {
    for (EngineListener listener : listeners) {
      listener.onEngineStop();
    }
    super.stop();
  }

  public void addEngineListener(EngineListener engineListener) {
    listeners.add(engineListener);
  }

  public void removeEngineListener(EngineListener engineListener) {
    listeners.add(engineListener);
  }
}
