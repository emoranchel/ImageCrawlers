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
public class StartEngineMethodHandler extends MethodInvoker implements
        EngineListener {

  public StartEngineMethodHandler(Object object, Method method) {
    super(object, method);
  }

  @Override
  public void onEngineStart() {
    invoke();
  }

  @Override
  public void onEngineStop() {
  }
}
