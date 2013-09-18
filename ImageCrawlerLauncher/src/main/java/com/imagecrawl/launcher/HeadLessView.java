package com.imagecrawl.launcher;

import com.imagecrawl.api.API;
import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.engine.XtendedEngine;
import com.imagecrawl.engine.XtendedEngineConfigurator;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.services.Analizer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.asmatron.messengine.annotations.EventMethod;
import org.asmatron.messengine.engines.Engine;

class HeadLessView implements View {

  private Engine engine;

  public HeadLessView(XtendedEngine engine) {
  }

  @Override
  public void setup(XtendedEngineConfigurator configurator) {
    configurator.setup(this);
  }

  @Override
  public void start(Engine engine, Analizer analizer) {
    this.engine = engine;
    AnalizeAction action = engine.get(API.Model.FACTORY).newAction();
    action.setStartPage(1);
    action.setEndPage(10);
    engine.send(API.Actions.ANALIZE, action);
  }

  @EventMethod(API.Events.IMAGE_FOUND_ID)
  public void imageFound(GalleryImage image) {
    log(image);
  }

  @EventMethod(API.Events.IMAGE_UPDATED_ID)
  public void imageUpdated(GalleryImage image) {
    log(image);
  }

  @EventMethod(API.Events.PROCESS_COMPLETE_ID)
  public void done() {
    System.out.println("DONE");
    engine.stop();
  }
  private Set<String> lastMsg = Collections.synchronizedSet(new HashSet<String>());

  private void log(GalleryImage image) {
    String logMsg = image.getPage() + "[" + image.getId() + "]" + image.getStatus();
    synchronized (this) {
      if (!lastMsg.contains(logMsg)) {
        System.out.println(logMsg);
        lastMsg.add(logMsg);
      }
    }
  }
}
