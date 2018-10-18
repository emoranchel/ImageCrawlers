package com.imagecrawl.tasks;

import org.apache.http.HttpResponse;

public interface HttpHandler {

  public HttpResponse get(String string) throws Exception;
  public HttpResponse getDownload(String string) throws Exception;

}
