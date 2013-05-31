package com.rip.danbooru;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import com.imagecrawl.model.GalleryTag;
import com.imagecrawl.model.HtmlTag;
import com.imagecrawl.tasks.MetadataTask;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public class DanbooruMetadataTask extends MetadataTask {

    public DanbooruMetadataTask(ExecutorService es, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> message) {
        super(es, messEngine, controlEngine, message);
    }

    @Override
    protected String getMetadataUrl() {
        return action.getMetadataUrl() + value.getDetailPath();
    }
    private boolean inTagList = false;
    private boolean inSize = false;
    private String tagType = "tag-type-general";

    @Override
    protected void scan(String line) {
        if (line.contains("<li>Rating: ")) {
            String rating = line.substring(line.indexOf("<li>Rating: ") + 12);
            rating = rating.substring(0, rating.indexOf("<")).trim();
            value.setRating(rating);
        }

        if (line.contains("Size:")) {
            inSize = true;
        }

        while (line.indexOf('<') >= 0 && line.indexOf('>') >= 0) {
            int openTagIndex = line.indexOf('<') + 1;
            int closeTagIndex = line.indexOf('>');
            if (openTagIndex < closeTagIndex) {
                String tag = line.substring(openTagIndex, closeTagIndex).trim();
                if (isTag(tag, "a", "li", "section")) {
                    HtmlTag htmlTag = new HtmlTag(tag);
                    handleTag(htmlTag);
                }
            }
            line = line.substring(closeTagIndex + 1);
        }
    }

    private void handleTag(HtmlTag tag) {
        if (tag.is("section") && tag.is(HtmlTag.Type.CLOSE)) {
            inTagList = false;
        }
        if (tag.is("li") && tag.is(HtmlTag.Type.CLOSE)) {
            tagType = "category-0";
            inSize = false;
        }
        if (tag.is("li") && tag.is(HtmlTag.Type.OPEN)) {
            String cssclass = tag.get("class");
            if (cssclass != null && cssclass.startsWith("category-")) {
                tagType = cssclass;
            }
        }
        if (tag.is("section") && tag.has("id", "tag-list") && tag.is(HtmlTag.Type.OPEN)) {
            inTagList = true;
        }
        if (tag.is("a")) {
            String href = tag.get("href");
            if (inTagList && href != null && href.startsWith("/posts?tags=")) {
                value.addTag(href.substring(12), getTagType(tagType));
            }
            if (inSize && href != null && href.startsWith("/data/")) {
                value.setRealUrl(href);
            }
        }
    }

    private GalleryTag.Type getTagType(String tagType) {
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
}
