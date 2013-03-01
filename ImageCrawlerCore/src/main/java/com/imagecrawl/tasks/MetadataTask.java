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

public abstract class MetadataTask extends ImageTask {

  public MetadataTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
  }

  @Override
  protected final void process() throws Exception {
    updateStatus(GalleryImage.Status.MetadataProcessing);
    HttpResponse response = httpGet(getMetadataUrl());
    if (response.getStatusLine().getStatusCode() != 200) {
      updateStatus(GalleryImage.Status.MetadataError, Integer.toString(response.getStatusLine().getStatusCode()));
      return;
    }

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        scan(line);
      }
    }
    if (value.getRealUrl() != null && value.getRealUrl().length() > 0) {
      updateStatus(GalleryImage.Status.MetadataComplete);
      forward(API.Messages.DOWNLOAD_IMAGE);
    } else {
      updateStatus(GalleryImage.Status.MetadataError, "Error parsing metadata");
    }
  }

  protected abstract String getMetadataUrl();

  protected abstract void scan(String line);

  public final boolean isTag(String tag, String... names) {
    for (String name : names) {
      if (tag.startsWith(name + " ")) {
        return true;
      }
      if (tag.startsWith("/" + name)) {
        return true;
      }
    }
    return false;
  }
}
