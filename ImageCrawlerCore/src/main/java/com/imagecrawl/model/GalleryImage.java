package com.imagecrawl.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GalleryImage {

  public enum Status {

    Discovered(StatusType.inProgress),
    Checking(StatusType.inProgress),
    AlreadyExist(StatusType.end),
    Checked(StatusType.inProgress),
    MetadataProcessing(StatusType.inProgress),
    MetadataError(StatusType.error),
    MetadataComplete(StatusType.inProgress),
    Downloading(StatusType.inProgress),
    Downloaded(StatusType.end),
    DownloadError(StatusType.error);
    private final StatusType type;

    private Status(StatusType type) {
      this.type = type;
    }

    public StatusType getType() {
      return type;
    }
  }

  public enum StatusType {

    inProgress, error, end;
  }
  private final int id;
  private String detailPath;
  private Status status = Status.Discovered;
  private String message = "";
  private int progress = 0;
  private String realUrl;
  private String rating;
  private List<GalleryTag> tags;
  private String page;
  private Object viewObject;
  private long lastUpdate = 0;

  public GalleryImage(int id) {
    this.id = id;
    this.tags = new ArrayList<>();
  }

  public int getId() {
    return id;
  }

  public void setDetailPath(String detailPath) {
    this.detailPath = detailPath;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getProgress() {
    return progress;
  }

  public void setProgress(int progress) {
    this.progress = progress;
  }

  @Override
  public String toString() {
    return Integer.toString(id);
  }

  public String getDetailPath() {
    return detailPath;
  }

  public String getRealUrl() {
    return realUrl;
  }

  public void setRealUrl(String realUrl) {
    this.realUrl = realUrl;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public List<GalleryTag> getTags() {
    return tags;
  }

  public void addTag(String name, GalleryTag.Type tagType) {
    tags.add(new GalleryTag(name, tagType));
  }

  public String getFileName() {
    String name = Integer.toString(id);
    String extension = realUrl.substring(realUrl.lastIndexOf(".") + 1);
    return name + "." + tagCloud() + "." + extension;
  }

  private String tagCloud() {
    StringBuilder tagCloud = new StringBuilder();
    List<GalleryTag> sortedTags = new ArrayList<>(tags);
    Collections.sort(sortedTags);
    for (GalleryTag tag : sortedTags) {
      if (tagCloud.length() > 180) {
        break;
      }
      if (tagCloud.length() > 0) {
        tagCloud.append('.');
      }
      tagCloud.append(tag.getName());
    }
    if (tagCloud.length() > 180) {
      tagCloud.setLength(180);
    }
    return tagCloud.toString();
  }

  public String getPage() {
    return page;
  }

  public void setPage(String page) {
    this.page = page;
  }

  public Object getViewObject() {
    return viewObject;
  }

  public void setViewObject(Object viewObject) {
    this.viewObject = viewObject;
  }

  public long getLastUpdate() {
    return lastUpdate;
  }

  public String getProgressAsStr() {
    return progress == 0 ? "" : (progress >= 100 ? "DONE" : (Integer.toString(progress) + "%"));
  }
  private String tagsCache = null;
  private int lastCount = 0;

  public String getTagsAsStr() {
    if (lastCount != tags.size()) {
      tagsCache = null;
    }
    if (tagsCache == null) {
      tagsCache = Arrays.toString(tags.toArray());
    }
    return tagsCache;
  }
}
