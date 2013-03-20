package com.imagecrawl.launcher;

import com.imagecrawl.crawlerswingview.Closing;
import com.imagecrawl.crawlerswingview.MainWindow;
import com.imagecrawl.engine.XtendedEngineConfigurator;
import com.imagecrawl.services.Analizer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.asmatron.messengine.ViewEngine;
import org.asmatron.messengine.engines.Engine;

class SwingView implements View {

    private MainWindow mainWindow;

    public SwingView(ViewEngine engine) {
        mainWindow = new MainWindow(engine);
    }

    @Override
    public void setup(XtendedEngineConfigurator configurator) {
        configurator.setup(mainWindow);
    }

    @Override
    public void start(final Engine engine, final Analizer analizer) {
        mainWindow.setVisible(true);

        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                engine.stop();
                Closing closingWindow = new Closing();
                closingWindow.setVisible(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            analizer.awaitTermination(1, TimeUnit.HOURS);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        System.exit(0);
                    }
                }).start();
            }
        });
    }
}
