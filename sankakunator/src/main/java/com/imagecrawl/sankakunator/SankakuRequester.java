package com.imagecrawl.sankakunator;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class SankakuRequester {

  private static final int interval = 300;
  private long lastRequest;

  public synchronized HttpResponse httpGet(String string) throws Exception {
    return httpGet(new HttpGet(string));
  }

  private synchronized HttpResponse doGet(HttpGet get) throws IOException, InterruptedException {
    long targetTime = lastRequest + interval;
    long currentTimeMillis = System.currentTimeMillis();
    if (targetTime > currentTimeMillis) {
      Thread.sleep(targetTime - currentTimeMillis);
    }
    HttpClient httpClient = new DefaultHttpClient();
    HttpResponse response = httpClient.execute(get);
    lastRequest = System.currentTimeMillis();
    return response;
  }

  public HttpResponse httpGet(HttpGet string) throws Exception {
    HttpResponse response = null;
    for (int i = 0; i < 5; i++) {
      response = doGet(string);
      if (response.getStatusLine().getStatusCode() == 200) {
        return response;
      }
      if (response.getStatusLine().getStatusCode() == 429) {
        Thread.sleep(2000);
      } else {
        Thread.sleep(500);
      }
    }
    return response;
  }
}
