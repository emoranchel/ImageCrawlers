/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagecrawl.engine;

import java.lang.reflect.Method;
import org.asmatron.messengine.util.MethodInvoker;

/**
 *
 * @author Eduardo
 */
public class StopEngineMethodHandler extends MethodInvoker implements
        EngineListener {

  public StopEngineMethodHandler(Object object, Method method) {
    super(object, method);
  }

  @Override
  public void onEngineStart() {
  }

  @Override
  public void onEngineStop() {
    invoke();
  }
}
