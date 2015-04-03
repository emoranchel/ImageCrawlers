package com.imagecrawl.api;

import org.asmatron.messengine.action.ActionObject;

public class AnalizeAction extends ActionObject {

    private int startPage;
    private int endPage;
    private int count = 0;
    private String savePath;
    private String analizeUrl;
    private String downloadUrl;
    private String metadataUrl;

    public int getStartPage() {
        return startPage;
    }

    public void setStartPage(int startPage) {
        this.startPage = startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public void setEndPage(int endPage) {
        this.endPage = endPage;
    }

    public void increaseCount() {
        count++;
    }

    public boolean isDone() {
        return count == 0;
    }

    public void decreaseCount() {
        count--;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getAnalizeUrl() {
        return analizeUrl;
    }

    public void setAnalizeUrl(String analizeUrl) {
        this.analizeUrl = analizeUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getMetadataUrl() {
        return metadataUrl;
    }

    public void setMetadataUrl(String metadataUrl) {
        this.metadataUrl = metadataUrl;
    }
}
