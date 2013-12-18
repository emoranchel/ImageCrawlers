/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagecrawl.engine;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asmatron.messengine.engines.DefaultEngine;

/**
 *
 * @author Eduardo
 */
public class XtendedEngine extends DefaultEngine {

  private final Set<EngineListener> listeners;
  private final Object endLock = new Object();

  public XtendedEngine() {
    super();
    this.listeners = new HashSet<>();

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
    synchronized (endLock) {
      endLock.notifyAll();
    }
  }

  public void addEngineListener(EngineListener engineListener) {
    listeners.add(engineListener);
  }

  public void removeEngineListener(EngineListener engineListener) {
    listeners.add(engineListener);
  }

  public void awaitStop() {
    synchronized (endLock) {
      try {
        endLock.wait();
      } catch (InterruptedException ex) {
        Logger.getLogger(XtendedEngine.class.getName()).log(Level.SEVERE, null, ex);
      }
      endLock.notifyAll();
    }

  }
}
