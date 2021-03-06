package com.rip.konachan.konachanator;

import com.imagecrawl.ImageCrawlFactory;
import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.tasks.DefaultCheckTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public class KonachanCrawlerFactory implements ImageCrawlFactory {
  
  private final ControlEngine controlEngine;
  private final MessEngine messEngine;
  
  public KonachanCrawlerFactory(ControlEngine controlEngine, MessEngine messEngine) {
    this.controlEngine = controlEngine;
    this.messEngine = messEngine;
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
    return Executors.newFixedThreadPool(2);
  }
  
  @Override
  public Runnable newDownloadTask(ExecutorService es, CrawlerMessage<GalleryImage> message) {
    return new KonachanDownloadTask(es, messEngine, controlEngine, message);
  }
  
  @Override
  public Runnable newCheckTask(ExecutorService es, CrawlerMessage<GalleryImage> message) {
    return new DefaultCheckTask(es, messEngine, controlEngine, message);
  }
  
  @Override
  public Runnable newAnalizeTask(ExecutorService es, CrawlerMessage<Integer> message) {
    return new KonachanAnalizeTask(es, messEngine, controlEngine, message);
  }
  
  @Override
  public Runnable newMetadataTask(ExecutorService es, CrawlerMessage<GalleryImage> message) {
    return new KonachanMetadataTask(es, messEngine, controlEngine, message);
  }
  
  @Override
  public AnalizeAction newAction() {
    AnalizeAction analizeAction = new AnalizeAction();
    analizeAction.setStartPage(1);
    analizeAction.setEndPage(10);
    analizeAction.setAnalizeUrl("http://konachan.com/post?tags=rating%3Asafe");
    analizeAction.setMetadataUrl("http://konachan.com/post/show/");
    analizeAction.setDownloadUrl("");
    analizeAction.setSavePath("konachan");
    return analizeAction;
  }
}
