package com.imagecrawl.model;

import java.net.URLDecoder;
import java.util.Objects;

public class GalleryTag implements Comparable<GalleryTag> {

  public enum Type {

    General(1), Character(5), Artist(4), Copyright(6), Style(2), Circle(3);
    private int value;

    private Type(int value) {
      this.value = value;
    }
  }
  private final String name;
  private final Type type;

  public GalleryTag(String name, Type type) {
    try {
      name = URLDecoder.decode(name, "UTF-8").replaceAll("\\ ", "_")
              .replaceAll("\\/", "-")
              .replaceAll("\\\\", "-");
      while (name.contains("__")) {
        name = name.replaceAll("\\_\\_", "_");
      }
      while (name.contains("--")) {
        name = name.replaceAll("\\-\\-", "-");
      }
    } catch (Exception ex) {
    }
    this.name = name;
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public Type getType() {
    return type;
  }

  @Override
  public String toString() {
    return type + ":" + name;
  }

  @Override
  public int compareTo(GalleryTag o) {
    return o.hashCode() - hashCode();
  }

  @Override
  public int hashCode() {
    return type.value;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final GalleryTag other = (GalleryTag) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    return Objects.equals(this.type, other.type);
  }
}
