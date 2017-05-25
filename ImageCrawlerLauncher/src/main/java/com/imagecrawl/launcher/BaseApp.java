package com.imagecrawl.launcher;

import com.imagecrawl.ImageCrawlFactory;
import com.imagecrawl.api.API;
import com.imagecrawl.sankakunator.SankakuCrawlerFactory;
import com.imagecrawl.sankakutop.SankakuTopCrawlerFactory;
import com.rip.danbooru.DanbooruCrawlerFactory;
import com.rip.konachan.konachanator.KonachanCrawlerFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;
import org.asmatron.messengine.engines.Engine;

public abstract class BaseApp {

  private final Parameters parameters;

  public static class Parameters {

    private final List<String> raw;
    private final List<String> unnamed;
    private final Map<String, String> named;

    private Parameters(String... args) {
      List<String> rawArgs = new ArrayList<>();
      List<String> unnamedArgs = new ArrayList<>();
      Map<String, String> namedArgs = new HashMap<>();
      if (args != null) {
        for (String arg : args) {
          rawArgs.add(arg);
          if (arg.indexOf('=') > 0) {
            namedArgs.put(arg.substring(0, arg.indexOf('=')), arg.substring(arg.indexOf('=') + 1));
          } else {
            unnamedArgs.add(arg);
          }
        }
      }
      this.raw = Collections.unmodifiableList(rawArgs);
      this.unnamed = Collections.unmodifiableList(unnamedArgs);
      this.named = Collections.unmodifiableMap(namedArgs);
    }

    public List<String> getRaw() {
      return raw;
    }

    public List<String> getUnnamed() {
      return unnamed;
    }

    public Map<String, String> getNamed() {
      return named;
    }
  }

  public Parameters getParameters() {
    return parameters;
  }

  public BaseApp(String[] args) {
    this.parameters = new Parameters(args);
  }

  public abstract void init();

  public abstract void stop();

  public abstract void start() throws Exception;

  protected void setupFactory(Engine engine) throws Exception {
    if (parameters.getRaw().contains("konachan")) {
      engine.set(API.Model.FACTORY, new KonachanCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "Konachan.com", null);
    } else if (parameters.getRaw().contains("sankaku")) {
      engine.set(API.Model.FACTORY, new SankakuCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "SankakuComplex.com", null);
    } else if (parameters.getRaw().contains("danbooru")) {
      engine.set(API.Model.FACTORY, new DanbooruCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "Danbooru.donmai.us", null);
    } else if (parameters.getRaw().contains("sankakuTop")) {
      engine.set(API.Model.FACTORY, new SankakuTopCrawlerFactory(engine, engine), null);
      engine.set(API.Model.TITLE, "SankakuTop.com", null);
    } else if (parameters.named.containsKey("-factory")) {
      engine.set(API.Model.TITLE, "custom", null);
      engine.set(API.Model.FACTORY, (ImageCrawlFactory) Class.forName(parameters.named.get("-factory")).getConstructor(ControlEngine.class, MessEngine.class).newInstance(engine, engine), null);
    }
  }

}
