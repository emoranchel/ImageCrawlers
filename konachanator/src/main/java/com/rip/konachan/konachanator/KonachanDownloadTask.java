/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rip.konachan.konachanator;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.tasks.DownloadTask;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

/**
 *
 * @author Eduardo
 */
public class KonachanDownloadTask extends DownloadTask {

  public KonachanDownloadTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
  }

  @Override
  protected String getDestinationFile() {
    return action.getSavePath() + "/" + value.getRating() + "/" + value.getFileName();
  }

  @Override
  protected String getSourceUrl() {
    return action.getDownloadUrl() + value.getRealUrl();
  }
}
