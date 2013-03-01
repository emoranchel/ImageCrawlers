package com.imagecrawl.tasks;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public final class DefaultCheckTask extends CheckTask {

  public DefaultCheckTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
  }

  @Override
  protected boolean fileExist() {
    File file = new File(action.getSavePath());
    Set<Integer> filesFound = new HashSet<>();
    addFiles(file, filesFound);
    return filesFound.contains(value.getId());
  }

  private void addFiles(File dir, Set<Integer> filesFound) {
    if (dir.exists()) {
      for (File file : dir.listFiles()) {
        if (file.isDirectory()) {
          addFiles(file, filesFound);
        } else {
          String name = file.getName();
          try {
            String idstr = name.substring(0, name.indexOf('.'));
            int id = Integer.parseInt(idstr);
            filesFound.add(id);
          } catch (Exception e) {
          }
        }
      }
    }
  }
}
