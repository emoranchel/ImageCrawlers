package com.imagecrawl.sankakunator;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.model.GalleryTag;
import com.imagecrawl.model.GalleryTag.Type;
import com.imagecrawl.model.HtmlTag;
import com.imagecrawl.tasks.MetadataTask;
import java.util.concurrent.ExecutorService;
import org.apache.http.HttpResponse;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public class SankakuMetadataTask extends MetadataTask {

    private final SankakuRequester requester;

    public SankakuMetadataTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage, SankakuRequester requester) {
        super(executorService, messEngine, controlEngine, crawlerMessage);
        this.requester = requester;
    }

    @Override
    protected String getMetadataUrl() {
        return action.getMetadataUrl() + value.getDetailPath();
    }
    private boolean inTagList = false;
    private String tagType = "tag-type-general";

    @Override
    protected void scan(String line) {
        if (line.contains("<li>Rating: ")) {
            String rating = line.substring(line.indexOf("<li>Rating: ") + 12);
            rating = rating.substring(0, rating.indexOf("<")).trim();
            value.setRating(rating);
        }
        if (line.contains("Original: ")) {
            for (HtmlTag tag : HtmlTag.parse(line)) {
                if (tag.is("a")) {
                    String href = tag.get("href");
                    if (href != null && href.trim().length() > 0) {
                        if(href.contains("?")){
                            href = href.substring(0, href.indexOf("?"));
                        }
                        value.setRealUrl(href);
                    }
                }
            }
        }
        if (line.contains("</ul>")) {
            inTagList = false;
        }
        if (line.contains("<ul id=\"tag-sidebar\">")) {
            inTagList = true;
        }
        if (inTagList) {
            for (HtmlTag tag : HtmlTag.parse(line)) {
                handleTag(tag);
            }
        }
    }

    private void handleTag(HtmlTag tag) {
        if (tag.is("li") && tag.is(HtmlTag.Type.CLOSE)) {
            tagType = "tag-type-general";
        }
        if (tag.is("li") && tag.is(HtmlTag.Type.OPEN)) {
            String cssclass = tag.get("class");
            if (cssclass != null && cssclass.startsWith("tag-type")) {
                tagType = cssclass;
            }
        }
        if (tag.is("a")) {
            String href = tag.get("href");
            if (inTagList && href != null && href.startsWith("/post/index?tags=")) {
                GalleryTag.Type type = getTagType(tagType.toLowerCase());
                GalleryTag galleryTag = new GalleryTag(href.substring(17), type);
                if (galleryTag.getName().length() > 3) {
                    value.getTags().add(galleryTag);
                }
            }
        }
    }

    private Type getTagType(String tagType) {
        switch (tagType.toLowerCase()) {
            case "tag-type-general":
                return GalleryTag.Type.General;
            case "tag-type-character":
                return GalleryTag.Type.Character;
            case "tag-type-artist":
                return GalleryTag.Type.Artist;
            case "tag-type-copyright":
                return GalleryTag.Type.Copyright;
            case "tag-type-style":
                return GalleryTag.Type.Style;
            case "tag-type-circle":
                return GalleryTag.Type.Circle;
        }
        return GalleryTag.Type.General;
    }

    @Override
    protected HttpResponse httpGet(String string) throws Exception {
        return requester.httpGet(string);
    }
}
