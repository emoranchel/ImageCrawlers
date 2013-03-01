/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rip.konachan.konachanator;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.tasks.AnalizeTask;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

/**
 *
 * @author Eduardo
 */
public class KonachanAnalizeTask extends AnalizeTask {
  
  public KonachanAnalizeTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<Integer> crawlerMessage) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
  }
  
  @Override
  protected void scan(String line) {
    while (line.contains("<a ")) {
      String tag = line.substring(line.indexOf("<a "));
      tag = tag.substring(0, tag.indexOf(">") + 1);
      line = line.substring(line.indexOf("<a ") + tag.length());
      if (tag.contains("href=\"")) {
        String href = tag.substring(tag.indexOf("href=\"") + 6);
        href = href.substring(0, href.indexOf("\""));
        if (href.startsWith("/post/show/")) {
          String path = href.substring(11);
          String idStr = path.substring(0, path.indexOf("/"));
          int id = Integer.parseInt(idStr);
          addImage(id, path);
        }
      }
    }
  }
  
  @Override
  protected String getUrl() {
    return action.getAnalizeUrl() + value;
  }
}
