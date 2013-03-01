/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagecrawl.sankakunator;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.tasks.DownloadTask;
import java.util.concurrent.ExecutorService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

/**
 *
 * @author Eduardo
 */
public class SankakuDownloadTask extends DownloadTask {

  private final SankakuRequester requester;

  public SankakuDownloadTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage, SankakuRequester requester) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
    this.requester = requester;
  }

  @Override
  protected String getDestinationFile() {
    return action.getSavePath() + "/" + value.getRating() + "/" + value.getFileName();
  }

  @Override
  protected String getSourceUrl() {
    return action.getDownloadUrl() + value.getRealUrl();
  }

  @Override
  protected HttpResponse httpGet(String string) throws Exception {
    HttpGet httpGet = new HttpGet(string);
    httpGet.addHeader("Referer", action.getMetadataUrl() + value.getDetailPath());
    return requester.httpGet(httpGet);
  }
}
