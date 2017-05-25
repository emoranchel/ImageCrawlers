package com.imagecrawl.launcher;

import com.imagecrawl.api.API;
import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.services.Analizer;
import com.imagecrawl.tasks.HttpHandler;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.asmatron.messengine.annotations.EngineStarted;
import org.asmatron.messengine.annotations.EventMethod;
import org.asmatron.messengine.engines.DefaultEngine;
import org.asmatron.messengine.engines.Engine;
import org.asmatron.messengine.engines.support.EngineConfigurator;

public class ConsoleApplication extends BaseApp {

  private Engine engine;

  public ConsoleApplication(String[] args) {
    super(args);
    this.engine = new DefaultEngine();
  }

  @Override
  public void start() throws Exception {
    setupFactory(engine);
    engine.set(API.Model.HTTP_HANDLER, new HttpHandler() {

      @Override
      public HttpResponse get(String string) throws Exception {
        System.out.println("REQUEST::" + string);
        int retry = 0;
        HttpResponse response = null;
        Exception ex = null;
        do {
          try {
            HttpClient httpClient = new DefaultHttpClient();
            response = httpClient.execute(new HttpGet(string));
            if (response.getStatusLine().getStatusCode() == 200) {
              return response;
            } else {
              throw new Exception("Response returned: " + response.getStatusLine());
            }
          } catch (Exception e) {
            ex = e;
            retry++;
            Thread.sleep(10000);

          }
        } while (retry < 3);
        if (response != null) {
          return response;
        }
        throw ex == null ? new RuntimeException() : ex;
      }
    }, null);
    Analizer analizer = new Analizer(engine, engine);
    new EngineConfigurator(engine).setup(analizer, this);
    engine.start();
  }

  @EngineStarted
  public void onEngineStarted() {
    AnalizeAction action = engine.get(API.Model.FACTORY).newAction();
    action.setStartPage(option(1, "-start"));
    action.setEndPage(option(10, "-end"));
    action.setSavePath(new File(".").getAbsolutePath());
    if (getParameters().getNamed().containsKey("-query")) {
      String url = action.getAnalizeUrl();
      if (url.indexOf('?') >= 0) {
        url = url.substring(0, url.indexOf('?'));
        action.setAnalizeUrl(url + "?" + getParameters().getNamed().get("-query"));
      }
    }
    engine.send(API.Actions.ANALIZE, action);
  }

  public static void main(String[] args) throws Exception {
    new ConsoleApplication(args).start();
  }

  private int option(int def, String param) {
    if (getParameters().getNamed().containsKey(param)) {
      return Integer.parseInt(getParameters().getNamed().get(param));
    }
    return def;
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
  private final Set<String> lastMsg = Collections.synchronizedSet(new HashSet<>());

  private void log(GalleryImage image) {
    String logMsg = image.getPage() + "[" + image.getId() + "]" + image.getStatus();
    synchronized (this) {
      if (!lastMsg.contains(logMsg)) {
        System.out.println(logMsg);
        lastMsg.add(logMsg);
      }
    }
  }

  @Override
  public void stop() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void init() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
