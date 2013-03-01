package com.imagecrawl.tasks;

import com.imagecrawl.api.API;
import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import org.apache.http.HttpResponse;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public abstract class AnalizeTask extends BaseTask<Integer> {

  private int count = 0;

  public AnalizeTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<Integer> crawlerMessage) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
  }

  @Override
  protected final void process() throws Exception {
    HttpResponse response = httpGet(getUrl());
    if (response.getStatusLine().getStatusCode() != 200) {
      return;
    }
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        scan(line);
      }
    }
  }

  protected abstract String getUrl();

  protected abstract void scan(String line);

  public final void addImage(int id, String detailPage) {
    GalleryImage galleryImage;
    galleryImage = new GalleryImage(id);
    galleryImage.setDetailPath(detailPage);
    addImage(galleryImage);
  }

  public final void addImage(GalleryImage image) {
    action.increaseCount();
    image.setPage(Integer.toString(value) + ":" + count);
    fireValueEvent(API.Events.IMAGE_FOUND, image);
    sendMessage(API.Messages.VERIFY_EXIST, image);
  }
}
