package com.imagecrawl.tasks;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public abstract class DownloadTask extends ImageTask {

    private static final String REGEX = ""
            + ""//                                 # Match a valid Windows filename (unspecified file system)
            + "^"//                                # Anchor to start of string
            + "(?!"//                              # Assert filename is not: CON, PRN,
            + "  (?:"//                            # AUX, NUL, COM1, COM2, COM3, COM4,
            + "    CON|PRN|AUX|NUL|"//             # COM5, COM6, COM7, COM8, COM9,
            + "    COM[1-9]|LPT[1-9]"//            # LPT1, LPT2, LPT3, LPT4, LPT5,
            + "  )"//                              # LPT6, LPT7, LPT8, and LPT9...
            + "  (?:\\.[^.]*)?"//                  # followed by optional extension
            + "  $"//                              # and end of string
            + ")"//                                # End negative lookahead assertion.
            + "[^<>:\"/\\\\|?*\\x00-\\x1F]*"//     # Zero or more valid filename chars.
            + "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]"//  # Last char is not a space or dot.
            + "$/";//                              # Anchor to end of string.
    private static final Pattern PATTERN = Pattern.compile(REGEX,
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);

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
            String destinationFileName = getDestinationFile();
            boolean absolutePath = destinationFileName.startsWith("/");
            String fixedFilename = getValidFileName(destinationFileName);
            if (absolutePath) {
                fixedFilename = "/" + fixedFilename;
            }
            File file = new File(fixedFilename);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
            try (
                    FileInputStream in = new FileInputStream(temporaryDownload);
                    FileOutputStream out = new FileOutputStream(file)) {
                IOUtils.copy(in, out);
            }
            value.setMessage(file.getAbsolutePath());
            updateStatus(GalleryImage.Status.Downloaded, 100);
            addToDownloads();
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

    public static String getValidFileName(String fileName) {
        String newFileName = fileName.replaceAll("\\?", "");
        while (newFileName.contains("..")) {
            newFileName = newFileName.replaceAll("\\.\\.", ".");
        }
        newFileName = newFileName.replaceAll(REGEX, "");
        newFileName = newFileName.replaceAll("\\\"", "'");
        newFileName = newFileName.replaceAll("\\*", "");
        if (newFileName.length() > 5) {
            newFileName = newFileName.substring(0, 4) + newFileName.substring(4).replaceAll("\\:", "-");
        }
        newFileName = newFileName.trim();
        while (newFileName.endsWith(".")) {
            newFileName = newFileName.substring(0, newFileName.length() - 1);
            newFileName = newFileName.trim();
        }
        if (newFileName.length() == 0) {
            throw new IllegalStateException(
                    "File Name " + fileName + " results in a empty fileName!");
        }
        return newFileName;
    }

    protected abstract String getSourceUrl();

    protected abstract String getDestinationFile();
    private static final Object lock = new Object();

    protected void addToDownloads() {
        synchronized (lock) {
            File dir = new File(action.getSavePath());
            try (FileOutputStream out = new FileOutputStream(new File(dir, "downloads.data"), true)) {
                ByteBuffer buffer = ByteBuffer.allocate(4);
                buffer.putInt(0, value.getId());
                out.write(buffer.array());
            } catch (Exception e) {
            }
        }
    }
}
