package com.imagecrawl.launcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  public void init() {
  }

  public void stop() {
  }

  public abstract void start();
}
