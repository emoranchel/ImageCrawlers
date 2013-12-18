package com.imagecrawl.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxApplication extends Application {

  private static MainWindowController controller;

  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader = new FXMLLoader(FxApplication.class.getResource("MainWindow.fxml"));
    loader.setController(controller);
    Parent root = (Parent) loader.load();
    stage.setTitle("title");
    stage.setScene(new Scene(root, 300, 275));
    stage.show();
  }

  public static void go(MainWindowController mainWindowController) {
    controller = mainWindowController;
    launch(new String[]{});
  }
}
