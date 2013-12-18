package com.imagecrawl.launcher;

public class ConsoleApplication extends BaseApp {

  public ConsoleApplication(String[] args) {
    super(args);
  }

  @Override
  public void start() {
  }

  public static void main(String[] args) {
    ConsoleApplication application = new ConsoleApplication(args);
    application.init();
    application.start();
    application.stop();

  }
}
