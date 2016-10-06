package com.imagecrawl.launcher;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.asmatron.messengine.engines.components.MessageConsumer;

public class App {

  public static void main(String[] args) throws Exception {
      Logger.getLogger(MessageConsumer.class.getName()).setLevel(Level.SEVERE);
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
}
