package com.imagecrawl.tasks;

import com.imagecrawl.api.CrawlerMessage;
import com.imagecrawl.model.GalleryImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import org.asmatron.messengine.ControlEngine;
import org.asmatron.messengine.MessEngine;

public final class DefaultCheckTask extends CheckTask {

    private final static Map<String, Set<Integer>> checkers = new HashMap<>();

    public DefaultCheckTask(ExecutorService executorService, MessEngine messEngine, ControlEngine controlEngine, CrawlerMessage<GalleryImage> crawlerMessage) {
        super(executorService, messEngine, controlEngine, crawlerMessage);
    }

    @Override
    protected boolean fileExist() {
        synchronized (checkers) {
            Set<Integer> ids = checkers.get(action.getSavePath());
            if (ids == null) {
                ids = new HashSet<>();
                addFiles(ids);
                addDownloads(ids);
                checkers.put(action.getSavePath(), ids);
            }
            boolean exist = ids.contains(value.getId());
            ids.add(value.getId());
            return exist;
        }
    }

    private void addFiles(final Set<Integer> ids) {
        try {
            File file = new File(action.getSavePath());
            Files.walkFileTree(Paths.get(file.toURI()), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String name = file.toFile().getName();
                    try {
                        String idstr = name.substring(0, name.indexOf('.'));
                        int id = Integer.parseInt(idstr);
                        ids.add(id);
                    } catch (Exception e) {
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addDownloads(Set<Integer> ids) {
        File dir = new File(action.getSavePath());
        try (FileInputStream input = new FileInputStream(new File(dir, "downloads.data"))) {
            byte[] buffer = new byte[4];
            ByteBuffer bb = ByteBuffer.wrap(buffer);
            while (input.read(buffer) > 0) {
                ids.add(bb.getInt());
                bb.rewind();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
