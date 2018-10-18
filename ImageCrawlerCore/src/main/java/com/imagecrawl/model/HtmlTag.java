package com.imagecrawl.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlTag {

  public enum Type {

    OPEN, CLOSE, COMPLETE
  }
  private String name;
  private final Map<String, String> attributes = new HashMap<>();
  private Type type;

  public HtmlTag(final String inputTag) {
    try {
      String tag = inputTag.trim();
      if (tag.startsWith("<")) {
        tag = tag.substring(1);
      }
      if (tag.endsWith(">")) {
        tag = tag.substring(0, tag.length() - 1);
      }
      if (tag.endsWith("/")) {
        tag = tag.substring(0, tag.indexOf("/"));
        type = Type.COMPLETE;
      } else if (tag.startsWith("/")) {
        tag = tag.substring(1);
        type = Type.CLOSE;
      } else {
        type = Type.OPEN;
      }
      StringBuilder str = new StringBuilder();
      boolean inQuotes = false;
      for (char c : tag.toCharArray()) {
        if (c == '"') {
          inQuotes = !inQuotes;
        }
        if (c == ' ' && !inQuotes) {
          flushStr(str);
        } else {
          str.append(c);
        }
      }
      flushStr(str);
    } catch (Exception e) {
      name = "KARMA";
      type = Type.COMPLETE;
    }
  }

  public String getName() {
    return name;
  }

  public String get(String attributeName) {
    return attributes.get(attributeName.toLowerCase());
  }

  private void flushStr(StringBuilder str) {
    if (str.length() > 0) {
      if (this.name == null) {
        name = str.toString();
      } else {
        if (str.indexOf("=") >= 0) {
          String attributeName = str.substring(0, str.indexOf("="));
          String attributeValue = str.substring(str.indexOf("=") + 1);
          if (attributeValue.startsWith("\"")) {
            attributeValue = attributeValue.substring(1, attributeValue.length() - 1);
          }
          attributes.put(attributeName.toLowerCase(), attributeValue);
        } else {
          String attribute = str.toString();
          attributes.put(attribute.toLowerCase(), attribute);
        }
      }
      str.setLength(0);
    }
  }

  public boolean is(String tagName) {
    return name.equalsIgnoreCase(tagName);
  }

  public boolean is(Type type) {
    return this.type == type;
  }

  public boolean has(String attribute) {
    return attributes.containsKey(attribute);
  }

  public boolean has(String attribute, String value) {
    return value.equals(get(attribute));
  }

  public static List<HtmlTag> parse(String line) {
    List<HtmlTag> tags = new ArrayList<>();
    while (line.indexOf('<') >= 0 && line.indexOf('>') >= 0) {
      int openTagIndex = line.indexOf('<') + 1;
      int closeTagIndex = line.indexOf('>');
      if (openTagIndex < closeTagIndex) {
        String tag = line.substring(openTagIndex, closeTagIndex).trim();
        tags.add(new HtmlTag(tag));
      }
      line = line.substring(closeTagIndex + 1);
    }
    return tags;
  }
}
