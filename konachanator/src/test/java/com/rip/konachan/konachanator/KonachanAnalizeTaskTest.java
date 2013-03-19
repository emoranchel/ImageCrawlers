package com.rip.konachan.konachanator;

import com.imagecrawl.api.AnalizeAction;
import com.imagecrawl.api.CrawlerMessage;
import static org.junit.Assert.*;
import org.junit.Test;

public class KonachanAnalizeTaskTest {

    @Test
    public void test1() {
        AnalizeAction action = new AnalizeAction();
        action.setAnalizeUrl("http://konachan.com/post?page=2&tags=");
        KonachanAnalizeTask task = new KonachanAnalizeTask(null, null, null, new CrawlerMessage<>(null, action, 1));
        assertEquals("http://konachan.com/post?tags=&page=1", task.getUrl());
    }
    @Test
    public void test2() {
        AnalizeAction action = new AnalizeAction();
        action.setAnalizeUrl("http://konachan.com/post");
        KonachanAnalizeTask task = new KonachanAnalizeTask(null, null, null, new CrawlerMessage<>(null, action, 1));
        assertEquals("http://konachan.com/post?page=1", task.getUrl());
    }
    @Test
    public void test3() {
        AnalizeAction action = new AnalizeAction();
        action.setAnalizeUrl("http://konachan.com/post?tags=");
        KonachanAnalizeTask task = new KonachanAnalizeTask(null, null, null, new CrawlerMessage<>(null, action, 1));
        assertEquals("http://konachan.com/post?tags=&page=1", task.getUrl());
    }
    @Test
    public void test4() {
        AnalizeAction action = new AnalizeAction();
        action.setAnalizeUrl("http://konachan.com/post?tags=%20rating:questionable");
        KonachanAnalizeTask task = new KonachanAnalizeTask(null, null, null, new CrawlerMessage<>(null, action, 1));
        assertEquals("http://konachan.com/post?tags=%20rating:questionable&page=1", task.getUrl());
    }
    @Test
    public void test5() {
        AnalizeAction action = new AnalizeAction();
        action.setAnalizeUrl("http://konachan.com/post?page=2&tags=rating%3Aquestionable");
        KonachanAnalizeTask task = new KonachanAnalizeTask(null, null, null, new CrawlerMessage<>(null, action, 1));
        assertEquals("http://konachan.com/post?tags=rating%3Aquestionable&page=1", task.getUrl());
    }
    @Test
    public void test6() {
        AnalizeAction action = new AnalizeAction();
        action.setAnalizeUrl("http://konachan.com/post?tags=rating%3Aquestionable&page=2");
        KonachanAnalizeTask task = new KonachanAnalizeTask(null, null, null, new CrawlerMessage<>(null, action, 1));
        assertEquals("http://konachan.com/post?tags=rating%3Aquestionable&page=1", task.getUrl());
    }
    @Test
    public void test7() {
        AnalizeAction action = new AnalizeAction();
        action.setAnalizeUrl("http://konachan.com/post?page=2&tags=rating%3Aquestionable");
        KonachanAnalizeTask task = new KonachanAnalizeTask(null, null, null, new CrawlerMessage<>(null, action, 1));
        assertEquals("http://konachan.com/post?tags=rating%3Aquestionable&page=1", task.getUrl());
    }
    @Test
    public void test8() {
        AnalizeAction action = new AnalizeAction();
        action.setAnalizeUrl("http://konachan.com/post??&&?&?&&&?&&&?&&???&&&&page=2&tags=rating%3Aquestionable");
        KonachanAnalizeTask task = new KonachanAnalizeTask(null, null, null, new CrawlerMessage<>(null, action, 1));
        assertEquals("http://konachan.com/post?tags=rating%3Aquestionable&page=1", task.getUrl());
    }
}
