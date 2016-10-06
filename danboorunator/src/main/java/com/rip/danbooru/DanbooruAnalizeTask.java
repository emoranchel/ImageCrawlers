package com.rip.danbooru;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.tasks.AnalizeTask;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public class DanbooruAnalizeTask extends AnalizeTask {

    public DanbooruAnalizeTask(ExecutorService es, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<Integer> message) {
        super(es, messEngine, controlEngine, message);
    }

    @Override
    protected String getUrl() {
        String analizeUrl = action.getAnalizeUrl();
        if (analizeUrl.contains("?page=")) {
            analizeUrl = analizeUrl.replaceAll("\\?page(\\=[^&]*)?(&|$)", "?");
        } else if (analizeUrl.contains("&page=")) {
            analizeUrl = analizeUrl.replaceAll("&page(\\=[^&]*)?(?=&|$)", "");
        }
        while (analizeUrl.contains("??") || analizeUrl.contains("?&") || analizeUrl.contains("&&")) {
            analizeUrl = analizeUrl.replaceAll("&&", "&");
            analizeUrl = analizeUrl.replaceAll("\\?\\?", "?");
            analizeUrl = analizeUrl.replaceAll("\\?&", "?");
        }
        if (!analizeUrl.contains("?")) {
            analizeUrl += "?page=";
        } else {
            analizeUrl += "&page=";
        }
        return analizeUrl + value;
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
                if (href.startsWith("/posts/")) {
                    String path = href.substring(7);
                    StringBuilder idStr = new StringBuilder();
                    for (char c : path.toCharArray()) {
                        if (Character.isDigit(c)) {
                            idStr.append(c);
                        } else {
                            break;
                        }
                    }
                    try{
                    int id = Integer.parseInt(idStr.toString());
                    addImage(id, path);
                    }catch(Exception e){}
                }
            }
        }
    }
}
