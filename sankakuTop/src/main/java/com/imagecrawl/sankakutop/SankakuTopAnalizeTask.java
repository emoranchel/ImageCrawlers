package com.imagecrawl.sankakutop;

import com.imagecrawl.api.API;
import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.sankakunator.SankakuRequester;
import com.imagecrawl.tasks.BaseTask;
import java.util.concurrent.ExecutorService;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import org.apache.http.HttpResponse;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

class SankakuTopAnalizeTask extends BaseTask<Integer> {

  private int count = 0;
  private final SankakuRequester requester;

  public SankakuTopAnalizeTask(ExecutorService es, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<Integer> message, SankakuRequester requester) {
    super(es, messEngine, controlEngine, message);
    this.requester = requester;
  }

  @Override
  protected void process() throws Exception {
    if (value == 1) {
      addJSON("http://www.sankakucomplex.com/chanbrowse/rssCache/idol_ero.JSON", "idol-h");
      addJSON("http://www.sankakucomplex.com/chanbrowse/rssCache/idol_safe.JSON", "idol-safe");
      addJSON("http://www.sankakucomplex.com/chanbrowse/rssCache/chan_safe.JSON", "chan-safe");
      addJSON("http://www.sankakucomplex.com/chanbrowse/rssCache/chan_ero.JSON", "chan-h");
    }
  }

  private void addJSON(String url, String rating) throws Exception {
    HttpResponse response = requester.httpGet(url);
    if (response.getStatusLine().getStatusCode() != 200) {
      return;
    }
    try (JsonReader jsonReader = Json.createReader(response.getEntity().getContent())) {
      JsonArray jsonArray = jsonReader.readArray();
      for (JsonObject jsonObject : jsonArray.getValuesAs(JsonObject.class)) {
        String detailPage = jsonObject.getString("href");
        int id = Integer.parseInt(detailPage.substring(detailPage.lastIndexOf('/')+1));
        GalleryImage galleryImage = new GalleryImage(id);
        galleryImage.setDetailPath(detailPage);
        galleryImage.setRating(rating);
        action.increaseCount();
        galleryImage.setPage(Integer.toString(value) + ":" + count);
        fireValueEvent(API.Events.IMAGE_FOUND, galleryImage);
        sendMessage(API.Messages.VERIFY_EXIST, galleryImage);
      }
    }
  }
}
