package com.imagecrawl.sankakutop;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.sankakunator.SankakuMetadataTask;
import com.imagecrawl.sankakunator.SankakuRequester;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public class SankakuTopMetadataTask extends SankakuMetadataTask {

  private String rating;

  public SankakuTopMetadataTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage, SankakuRequester requester) {
    super(executorService, messEngine, controlEngine, crawlerMessage, requester);
    rating = value.getRating();
  }

  @Override
  protected void scan(String line) {
    super.scan(line);
    value.setRating(rating);
  }
}
