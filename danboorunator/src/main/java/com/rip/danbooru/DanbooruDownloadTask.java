package com.rip.danbooru;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.tasks.DownloadTask;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

class DanbooruDownloadTask extends DownloadTask {

    public DanbooruDownloadTask(ExecutorService es, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> message) {
        super(es, messEngine, controlEngine, message);
    }

    @Override
    protected String getDestinationFile() {
        return action.getSavePath() + "/" + value.getRating() + "/" + value.getFileName();
    }

    @Override
    protected String getSourceUrl() {
        return action.getDownloadUrl() + value.getRealUrl();
    }
}
