package com.imagecrawl.imagecrawlerdownloader;

import com.imagecrawl.tasks.DownloadTask;
import static org.junit.Assert.*;
import org.junit.Test;

public class DownloadTaskTest {

    @Test
    public void testPaths() {
        assertEquals(
                "/D:/pictures/konachan/Questionable/156229.desu_yo.jpg", //
                DownloadTask.getValidFileName(
                "/D:/pictures/konachan/Questionable/156229.desu_yo?.jpg" //
                ));

        assertEquals(
                "/D:/pictures/konachan/Questionable/156229.desu_yo-a.jpg", //
                DownloadTask.getValidFileName(
                "/D:/pictures/konachan/Questionable/156229.desu_yo:a.jpg" //
                ));

        assertEquals(
                "D:/pictures/danbooru/Safe/1431199.k-on!.akiyama_mio.hirasawa_yui.kotobuki_tsumugi.tainaka_ritsu.2girls.4girls.black_eyes.black_hair.blonde_hair.blue_eyes.blush.brown_eyes.brown_hair.long_hair.lowres.multiple_girls.jpg", //
                DownloadTask.getValidFileName(
                "D:/pictures/danbooru/Safe/1431199.k-on!.akiyama_mio.hirasawa_yui.kotobuki_tsumugi.tainaka_ritsu.2girls.4girls.black_eyes.black_hair.blonde_hair.blue_eyes.blush.brown_eyes.brown_hair.long_hair.lowres.multiple_girls.jpg" //
                ));
        assertEquals(
                "D:/pictures/danbooru/Safe/1431227.idolmaster.idolmaster_cinderella_girls.imai_kana.mokufuu.1girl", //
                DownloadTask.getValidFileName(
                "D:/pictures/danbooru/Safe/1431227.idolmaster.idolmaster_cinderella_girls.imai_kana.mokufuu.1girl." //
                ));
        assertEquals(
                "D:/pictures/danbooru/Safe/1431227.idolmaster.idolmaster_cinderella_girls.imai_kana.mokufuu.1girl.jpg", //
                DownloadTask.getValidFileName(
                "D:/pictures/danbooru/Safe/1431227.idolmaster.idolmaster_cinderella_girls.imai_kana.mokufuu.1girl..jpg" //
                ));

    }
}
