package com.imagecrawl.tasks;

import com.imagecrawl.api.API;
import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public abstract class CheckTask extends ImageTask {

  public CheckTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
  }

  @Override
  protected final void process() throws Exception {
    updateStatus(GalleryImage.Status.Checking);
    if (fileExist()) {
      updateStatus(GalleryImage.Status.AlreadyExist);
    } else {
      updateStatus(GalleryImage.Status.Checked);
      forward(API.Messages.GET_DATA_IMAGE);
    }
  }

  protected abstract boolean fileExist();
}
