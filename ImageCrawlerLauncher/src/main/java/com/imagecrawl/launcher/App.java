package com.imagecrawl.launcher;

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
}
