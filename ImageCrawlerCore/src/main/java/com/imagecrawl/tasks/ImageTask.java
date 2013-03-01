package com.imagecrawl.tasks;

import com.imagecrawl.api.API;
import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public abstract class ImageTask extends BaseTask<GalleryImage> {

  public ImageTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
  }

  public void updateStatus(GalleryImage.Status status) {
    value.setStatus(status);
    notifyUpdate();
  }

  public void updateStatus(GalleryImage.Status status, int progress) {
    value.setProgress(progress);
    updateStatus(status);
  }

  public void updateStatus(GalleryImage.Status status, String message) {
    value.setMessage(message);
    updateStatus(status);
  }

  public void forward(String message) {
    action.increaseCount();
    sendMessage(message, value);
  }

  public void notifyUpdate() {
    fireValueEvent(API.Events.IMAGE_UPDATED, value);
  }
}
