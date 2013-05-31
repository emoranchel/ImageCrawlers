package com.imagecrawl.imagecrawlerdownloader;

import com.imagecrawl.tasks.DownloadTask;
import static org.junit.Assert.*;
import org.junit.Test;

public class DownloadTaskTest {

    @Test
    public void testPaths() {
        assertEquals(
                "/D:/pictures/konachan/Questionable/156229.desu_yo.jpg",
                DownloadTask.getValidFileName(
                "/D:/pictures/konachan/Questionable/156229.desu_yo?.jpg"
                ));
    }
}
