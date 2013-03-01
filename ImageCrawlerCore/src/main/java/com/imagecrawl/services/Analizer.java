package com.imagecrawl.services;

import com.imagecrawl.ImageCrawlFactory;
import com.imagecrawl.api.API;
import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.engine.EngineListener;
import com.imagecrawl.model.GalleryImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;
import org.asmatron.messengine.annotations.ActionMethod;
import org.asmatron.messengine.annotations.MessageMethod;

public class Analizer implements EngineListener {

  private final ControlEngine controlEngine;
  private final MessEngine messEngine;
  private ImageCrawlFactory crawlFactory;
  private ExecutorService analizerExecutorService;
  private ExecutorService downloadExecutorService;
  private ExecutorService metadataExecutorService;

  public Analizer(ControlEngine controlEngine, MessEngine messEngine) {
    this.controlEngine = controlEngine;
    this.messEngine = messEngine;
  }

  @Override
  public void onEngineStart() {
    crawlFactory = controlEngine.get(API.Model.FACTORY);
    analizerExecutorService = crawlFactory.getAnalizerExecutorService();
    metadataExecutorService = crawlFactory.getMetadataExecutorService();
    downloadExecutorService = crawlFactory.getDownloadExecutorService();
  }

  @ActionMethod(API.Actions.ANALIZE_ID)
  public void analize(AnalizeAction analizeAction) {
    for (int i = analizeAction.getStartPage(); i < analizeAction.getEndPage(); i++) {
      analizeAction.increaseCount();
      messEngine.send(new CrawlerMessage<>(API.Messages.ANALIZE_PAGE_IMAGE, analizeAction, i));
    }
  }

  @MessageMethod(API.Messages.VERIFY_EXIST)
  public void check(CrawlerMessage<GalleryImage> message) {
    Runnable task = crawlFactory.newCheckTask(metadataExecutorService, message);
    metadataExecutorService.submit(task);
  }

  @MessageMethod(API.Messages.ANALIZE_PAGE_IMAGE)
  public void analizePage(CrawlerMessage<Integer> message) {
    Runnable task = crawlFactory.newAnalizeTask(analizerExecutorService, message);
    analizerExecutorService.submit(task);
  }

  @MessageMethod(API.Messages.GET_DATA_IMAGE)
  public void getData(CrawlerMessage<GalleryImage> message) {
    Runnable task = crawlFactory.newMetadataTask(metadataExecutorService, message);
    metadataExecutorService.submit(task);
  }

  @MessageMethod(API.Messages.DOWNLOAD_IMAGE)
  public void download(CrawlerMessage<GalleryImage> message) {
    Runnable task = crawlFactory.newDownloadTask(downloadExecutorService, message);
    downloadExecutorService.submit(task);
  }

  @MessageMethod(API.Messages.PROCESS_COMPLETE)
  public void onProcesscomplete(CrawlerMessage<Void> message) {
    message.getAction().decreaseCount();
    if (message.getAction().isDone()) {
      controlEngine.fireEvent(API.Events.PROCESS_COMPLETE);
    }
  }

  @Override
  public void onEngineStop() {
    analizerExecutorService.shutdown();
    metadataExecutorService.shutdown();
    downloadExecutorService.shutdown();
  }

  public void awaitTermination(long timeout, TimeUnit timeUnit) throws InterruptedException {
    analizerExecutorService.awaitTermination(timeout, timeUnit);
    metadataExecutorService.awaitTermination(timeout, timeUnit);
    downloadExecutorService.awaitTermination(timeout, timeUnit);
  }
}
