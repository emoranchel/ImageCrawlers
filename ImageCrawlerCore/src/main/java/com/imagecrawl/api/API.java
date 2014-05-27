package com.imagecrawl.api;

import com.imagecrawl.ImageCrawlFactory;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.tasks.HttpHandler;
import org.asmatron.messengine.action.ActionId;
import org.asmatron.messengine.event.EmptyEvent;
import org.asmatron.messengine.event.EventId;
import org.asmatron.messengine.event.ValueEvent;
import org.asmatron.messengine.model.ModelId;

public interface API {

  public interface Model {
    String HTTP_HANDLER_ID = "httpHandlerId";
    ModelId<HttpHandler> HTTP_HANDLER = ModelId.readOnly(HTTP_HANDLER_ID);
    String TITLE_ID = "title";
    ModelId<String> TITLE = ModelId.readOnly(TITLE_ID);
    String FACTORY_ID = "factory";
    ModelId<ImageCrawlFactory> FACTORY = ModelId.readOnly(FACTORY_ID);
  }

  public interface Events {

    String PROCESS_COMPLETE_ID = "processCommplete";
    EventId<EmptyEvent> PROCESS_COMPLETE = EventId.ev(PROCESS_COMPLETE_ID);
    String IMAGE_FOUND_ID = "newImage";
    EventId<ValueEvent<GalleryImage>> IMAGE_FOUND = EventId.ev(IMAGE_FOUND_ID);
    String IMAGE_UPDATED_ID = "imageUpdated";
    EventId<ValueEvent<GalleryImage>> IMAGE_UPDATED = EventId.ev(IMAGE_UPDATED_ID);
  }

  public interface Actions {

    String ANALIZE_ID = "analize";
    ActionId<AnalizeAction> ANALIZE = ActionId.cm(ANALIZE_ID);
  }

  public interface Messages {

    String PROCESS_COMPLETE = "processComplete";
    String DOWNLOAD_IMAGE = "dowloadImage";
    String GET_DATA_IMAGE = "getData";
    String ANALIZE_PAGE_IMAGE = "analizePage";
    String VERIFY_EXIST = "verifyExist";
  }
}
