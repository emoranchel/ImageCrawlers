package com.imagecrawl.launcher;

import com.imagecrawl.api.API;
import com.imagecrawl.sankakunator.SankakuCrawlerFactory;
import com.imagecrawl.sankakutop.SankakuTopCrawlerFactory;
import com.rip.danbooru.DanbooruCrawlerFactory;
import com.rip.konachan.konachanator.KonachanCrawlerFactory;
import org.asmatron.messengine.engines.Engine;

public class App {

  public static void main(String[] args) throws Exception {
    if (containArg(args, "-fx")) {
      FxApplication.main(args);
    } else if (containArg(args, "-swing")) {
      SwingApplication.main(args);
    } else {
      ConsoleApplication.main(args);
    }
  }

  private static boolean containArg(String[] args, String param) {
    for (String arg : args) {
      if (param.equalsIgnoreCase(arg)) {
        return true;
      }
    }
    return false;
  }

  public static void setupFactory(String[] args, Engine engine) {
    if (containArg(args, "konachan")) {
      engine.set(API.Model.FACTORY, new KonachanCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "Konachan.com", null);
    } else if (containArg(args, "sankaku")) {
      engine.set(API.Model.FACTORY, new SankakuCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "SankakuComplex.com", null);
    } else if (containArg(args, "danbooru")) {
      engine.set(API.Model.FACTORY, new DanbooruCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "Danbooru.donmai.us", null);
    } else if (containArg(args, "sankakuTop")) {
      engine.set(API.Model.FACTORY, new SankakuTopCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "SankakuTop.com", null);
    }
  }
}
