package com.imagecrawl.sankakutop;

import com.imagecrawl.ImageCrawlFactory;
import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.sankakunator.SankakuDownloadTask;
import com.imagecrawl.sankakunator.SankakuRequester;
import com.imagecrawl.tasks.DefaultCheckTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public class SankakuTopCrawlerFactory implements ImageCrawlFactory {

  private final ControlEngine controlEngine;
  private final MessEngine messEngine;
  private SankakuRequester requester;

  public SankakuTopCrawlerFactory(ControlEngine controlEngine, MessEngine messEngine) {
    this.controlEngine = controlEngine;
    this.messEngine = messEngine;
    this.requester = new SankakuRequester();
  }

  @Override
  public ExecutorService getAnalizerExecutorService() {
    return Executors.newSingleThreadExecutor();
  }

  @Override
  public ExecutorService getMetadataExecutorService() {
    return Executors.newSingleThreadExecutor();
  }

  @Override
  public ExecutorService getDownloadExecutorService() {
    return Executors.newFixedThreadPool(4);
  }

  @Override
  public Runnable newAnalizeTask(ExecutorService es, CrawlerMessage<Integer> message) {
    return new SankakuTopAnalizeTask(es, messEngine, controlEngine, message, requester);
  }

  @Override
  public Runnable newMetadataTask(ExecutorService es, CrawlerMessage<GalleryImage> message) {
    return new SankakuTopMetadataTask(es, messEngine, controlEngine, message, requester);
  }

  @Override
  public Runnable newCheckTask(ExecutorService es, CrawlerMessage<GalleryImage> message) {
    return new DefaultCheckTask(es, messEngine, controlEngine, message);
  }

  @Override
  public Runnable newDownloadTask(ExecutorService es, CrawlerMessage<GalleryImage> message) {
    return new SankakuDownloadTask(es, messEngine, controlEngine, message, requester);
  }

  @Override
  public AnalizeAction newAction() {
    AnalizeAction analizeAction = new AnalizeAction();
    analizeAction.setStartPage(1);
    analizeAction.setEndPage(10);
    analizeAction.setAnalizeUrl("");
    analizeAction.setMetadataUrl("");
    analizeAction.setDownloadUrl("");
    analizeAction.setSavePath("/D:/pictures/sankakuTop");
    return analizeAction;
  }
}
