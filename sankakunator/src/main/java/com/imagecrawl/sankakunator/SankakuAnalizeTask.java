/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagecrawl.sankakunator;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.model.HtmlTag;
import com.imagecrawl.tasks.AnalizeTask;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.apache.http.HttpResponse;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

/**
 *
 * @author Eduardo
 */
public class SankakuAnalizeTask extends AnalizeTask {

  private final SankakuRequester requester;

  public SankakuAnalizeTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<Integer> crawlerMessage, SankakuRequester requester) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
    this.requester = requester;
  }

  @Override
  protected void scan(String line) {
    if (line.contains("<a")) {
      List<HtmlTag> tags = HtmlTag.parse(line);
      for (int i = 0; i < tags.size(); i++) {
        HtmlTag tag = tags.get(i);
        if (tag.is("a")) {
          String href = tag.get("href");
          if (href != null && href.startsWith("/post/show/")) {
            String idStr = href.substring(11);
            int id = Integer.parseInt(idStr);
            GalleryImage image = new GalleryImage(id);
            image.setDetailPath(idStr);
            String title = null;
            try {
              title = tags.get(i + 1).get("title");
            } catch (Exception ex) {
            }
            if (title != null) {
              for (String imageTag : title.split("\\ ")) {
                if (imageTag.startsWith("rating:")) {
                  image.setRating(imageTag.substring(7));
                }
              }
            }
            addImage(image);
          }

        }
      }
    }
  }

  @Override
  protected String getUrl() {
    return action.getAnalizeUrl() + value;
  }

  @Override
  protected HttpResponse httpGet(String string) throws Exception {
    return requester.httpGet(string);
  }
}
