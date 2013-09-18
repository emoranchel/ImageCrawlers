package com.imagecrawl.launcher;

import com.imagecrawl.api.API;
import com.imagecrawl.engine.XtendedEngine;
import com.imagecrawl.engine.XtendedEngineConfigurator;
import com.imagecrawl.sankakunator.SankakuCrawlerFactory;
import com.imagecrawl.sankakutop.SankakuTopCrawlerFactory;
import com.imagecrawl.services.Analizer;
import com.rip.danbooru.DanbooruCrawlerFactory;
import com.rip.konachan.konachanator.KonachanCrawlerFactory;

public class App {

  private static final boolean javaFxEnabled = false;

  public static void main(String[] args) throws Exception {
    final XtendedEngine engine = new XtendedEngine();

    final Analizer analizer = new Analizer(engine, engine);

    XtendedEngineConfigurator configurator = new XtendedEngineConfigurator(engine);
    configurator.setup(analizer);

    if (containArg(args, "sankaku")) {
      engine.set(API.Model.FACTORY, new SankakuCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "Konachan.com", null);
    } else if (containArg(args, "danbooru")) {
      engine.set(API.Model.FACTORY, new DanbooruCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "Danbooru.donmai.us", null);
    } else if (containArg(args, "sankakutop")) {
      engine.set(API.Model.FACTORY, new SankakuTopCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "SankakuTop.com", null);      
    } else {
      engine.set(API.Model.FACTORY, new KonachanCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "Konachan.com", null);
    }

    View view;
    if (containArg(args, "fx") && javaFxEnabled) {
      view = new FxView(engine);
    } else if(containArg(args, "headless")){
      view = new HeadLessView(engine);
    } else {
      view = new SwingView(engine);
    } 
    view.setup(configurator);

    engine.start();

    view.start(engine, analizer);
  }

  private static boolean containArg(String[] args, String param) {
    for (String arg : args) {
      if (param.equalsIgnoreCase(arg)) {
        return true;
      }
    }
    return false;
  }
}
