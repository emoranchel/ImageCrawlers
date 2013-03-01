package com.imagecrawl.api;

import org.asmatron.messengine.messaging.SimpleMessage;

public class CrawlerMessage<T extends Object> extends SimpleMessage<T> {

  private final AnalizeAction action;

  public CrawlerMessage(String type, AnalizeAction action, T body) {
    super(type, body);
    this.action = action;
  }

  public AnalizeAction getAction() {
    return action;
  }
}
