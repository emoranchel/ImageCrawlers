package com.imagecrawl.sankakunator;

import com.imagecrawl.ImageCrawlFactory;
import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.tasks.DefaultCheckTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public class SankakuCrawlerFactory implements ImageCrawlFactory {
  
  private final ControlEngine controlEngine;
  private final MessEngine messEngine;
  private SankakuRequester requester;
  
  public SankakuCrawlerFactory(ControlEngine controlEngine, MessEngine messEngine) {
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
  public Runnable newDownloadTask(ExecutorService es, CrawlerMessage<GalleryImage> message) {
    return new SankakuDownloadTask(es, messEngine, controlEngine, message, requester);
  }
  
  @Override
  public Runnable newCheckTask(ExecutorService es, CrawlerMessage<GalleryImage> message) {
    return new DefaultCheckTask(es, messEngine, controlEngine, message);
  }
  
  @Override
  public Runnable newAnalizeTask(ExecutorService es, CrawlerMessage<Integer> message) {
    return new SankakuAnalizeTask(es, messEngine, controlEngine, message, requester);
  }
  
  @Override
  public Runnable newMetadataTask(ExecutorService es, CrawlerMessage<GalleryImage> message) {
    return new SankakuMetadataTask(es, messEngine, controlEngine, message, requester);
  }
  
  @Override
  public AnalizeAction newAction() {
    AnalizeAction analizeAction = new AnalizeAction();
    analizeAction.setStartPage(1);
    analizeAction.setEndPage(10);
    analizeAction.setAnalizeUrl("http://chan.sankakucomplex.com/post/index.content?tags=rating%3Asafe");
    analizeAction.setMetadataUrl("http://chan.sankakucomplex.com/post/show/");
    analizeAction.setDownloadUrl("");
    analizeAction.setSavePath("/D:/pictures/sankakuComplex");
    return analizeAction;
  }
}
