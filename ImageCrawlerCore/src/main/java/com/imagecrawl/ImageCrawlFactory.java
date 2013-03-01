package com.imagecrawl;

import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import java.util.concurrent.ExecutorService;

public interface ImageCrawlFactory {
  
  public AnalizeAction newAction();

  public ExecutorService getAnalizerExecutorService();

  public ExecutorService getMetadataExecutorService();

  public ExecutorService getDownloadExecutorService();

  public Runnable newDownloadTask(ExecutorService downloadExecutorService, CrawlerMessage<GalleryImage> message);

  public Runnable newCheckTask(ExecutorService metadataExecutorService, CrawlerMessage<GalleryImage> message);

  public Runnable newAnalizeTask(ExecutorService analizerExecutorService, CrawlerMessage<Integer> message);

  public Runnable newMetadataTask(ExecutorService metadataExecutorService, CrawlerMessage<GalleryImage> message);
}
