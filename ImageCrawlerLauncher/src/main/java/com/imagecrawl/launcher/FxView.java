package com.imagecrawl.launcher;

import com.imagecrawl.api.API;
import com.imagecrawl.engine.XtendedEngineConfigurator;
import com.imagecrawl.fx.MainWindowController;
import com.imagecrawl.services.Analizer;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.asmatron.messengine.ViewEngine;
import org.asmatron.messengine.engines.Engine;

class FxView extends Application implements View {
    private MainWindowController controller;

    public FxView(ViewEngine engine) {
        this.controller = new MainWindowController(engine);
    }

    @Override
    public void setup(XtendedEngineConfigurator configurator) {
        configurator.setup(controller);
    }

    @Override
    public void start(Engine engine, Analizer analizer) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml_example.fxml"));
        loader.setController(controller);
        Parent root = (Parent) loader.load();
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}
