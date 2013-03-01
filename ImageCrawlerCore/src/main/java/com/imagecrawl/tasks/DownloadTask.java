package com.imagecrawl.tasks;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public abstract class DownloadTask extends ImageTask {

  public DownloadTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage) {
    super(executorService, messEngine, controlEngine, crawlerMessage);
  }

  @Override
  protected void process() throws Exception {
    File temporaryDownload = null;
    try {
      temporaryDownload = File.createTempFile("download", Integer.toString(value.getId()));
    } catch (IOException ex) {
      Logger.getLogger(DownloadTask.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (temporaryDownload == null) {
      updateStatus(GalleryImage.Status.DownloadError, "Can't create temporary file");
    }
    try {
      updateStatus(GalleryImage.Status.Downloading);
      HttpResponse response = httpGet(getSourceUrl());
      if (response.getStatusLine().getStatusCode() != 200) {
        updateStatus(GalleryImage.Status.DownloadError, "HttpError [" + response.getStatusLine().getStatusCode() + "]");
        return;
      }
      try (
              InputStream content = response.getEntity().getContent();
              FileOutputStream out = new FileOutputStream(temporaryDownload)) {
        long count = 0;
        long total = response.getEntity().getContentLength();
        int n = 0;
        byte[] buffer = new byte[1024 * 4];
        int t = 0;
        while (-1 != (n = content.read(buffer))) {
          out.write(buffer, 0, n);
          count += n;
          if (count != 0 && total != 0 && t == 10) {
            updateStatus(GalleryImage.Status.Downloading, (int) (((double) count / total) * 100));
            t = 0;
          }
          t++;
        }
      }
      File file = new File(getValidFileName(getDestinationFile()));
      if (!file.getParentFile().exists()) {
        file.getParentFile().mkdirs();
      }
      try (
              FileInputStream in = new FileInputStream(temporaryDownload);
              FileOutputStream out = new FileOutputStream(file)) {
        IOUtils.copy(in, out);
      }
      updateStatus(GalleryImage.Status.Downloaded, 100);
    } catch (Exception ex) {
      Logger.getLogger(DownloadTask.class.getName()).log(Level.SEVERE, null, ex);
      updateStatus(GalleryImage.Status.DownloadError, ex.getClass().getName() + ":" + ex.getMessage());
    } finally {
      try {
        temporaryDownload.delete();
      } catch (Exception e) {
        try {
          temporaryDownload.deleteOnExit();
        } catch (Exception e2) {
        }
      }
    }
  }

  public static boolean validateFileName(String fileName) {
    return fileName.matches("^[^.\\\\/:*?\"<>|]?[^\\\\/:*?\"<>|]*")
            && getValidFileName(fileName).length() > 0;
  }

  public static String getValidFileName(String fileName) {
    String newFileName = fileName.replaceAll("^[.\\\\/:*?\"<>|]?[\\\\/:*?\"<>|]*", "");
    if (newFileName.length() == 0) {
      throw new IllegalStateException(
              "File Name " + fileName + " results in a empty fileName!");
    }
    return newFileName;
  }

  protected abstract String getSourceUrl();

  protected abstract String getDestinationFile();
}
