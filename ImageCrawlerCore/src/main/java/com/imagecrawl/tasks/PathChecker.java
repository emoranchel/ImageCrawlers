/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imagecrawl.tasks;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;
import static java.nio.file.StandardWatchEventKinds.*;
import java.nio.file.WatchEvent;

/**
 *
 * @author Eduardo
 */
class PathChecker {

    private final Set<Integer> ids = new HashSet<>();
    private WatchService watcher;

    PathChecker(String savePath) {
        try {
            watcher = FileSystems.getDefault().newWatchService();
            addFiles(savePath);
        } catch (Exception e) {
        }
    }

    boolean exist(int id) {
        WatchKey key = watcher.poll();
        while (key != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();
                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) {
                    continue;
                }
                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();

                //Path child = dir.resolve(name);

                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if ((kind == ENTRY_CREATE)) {
                    add(name);
//                    try {
//                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
//                            registerAll(child);
//                        }
//                    } catch (IOException x) {
//                        // ignore to keep sample readbale
//                    }
                }
            }
            key = watcher.poll();
        }
        return ids.contains(id);
    }

    private void addFiles(String path) throws IOException {
        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                add(file);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private void add(Path file) {
        String name = file.toFile().getName();
        try {
            String idstr = name.substring(0, name.indexOf('.'));
            int id = Integer.parseInt(idstr);
            ids.add(id);
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>) event;
    }
}
