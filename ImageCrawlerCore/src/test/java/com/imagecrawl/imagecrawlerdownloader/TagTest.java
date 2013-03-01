package com.imagecrawl.imagecrawlerdownloader;

import com.imagecrawl.model.HtmlTag;
import static org.junit.Assert.*;
import org.junit.Test;

public class TagTest {

  @Test
  public void testTag1() throws Exception {
    HtmlTag tag = new HtmlTag("a href=\"something\"");
    assertEquals("a", tag.getName());
    assertEquals("something", tag.get("href"));
    assertTrue(tag.is(HtmlTag.Type.OPEN));
  }

  @Test
  public void testTag2() throws Exception {
    HtmlTag tag = new HtmlTag("ul");
    assertEquals("ul", tag.getName());
  }

  @Test
  public void testTag3() throws Exception {
    HtmlTag tag = new HtmlTag("a href=\"something is wrong here\"");
    assertEquals("a", tag.getName());
    assertEquals("something is wrong here", tag.get("href"));
  }

  @Test
  public void testTag4() throws Exception {
    HtmlTag tag = new HtmlTag("a href=something style=none");
    assertEquals("a", tag.getName());
    assertEquals("something", tag.get("href"));
    assertEquals("none", tag.get("style"));
  }

  @Test
  public void testTag5() throws Exception {
    HtmlTag tag = new HtmlTag("/a href=\"something\"");
    assertEquals("a", tag.getName());
    assertEquals("something", tag.get("href"));
    assertTrue(tag.is(HtmlTag.Type.CLOSE));
  }

  @Test
  public void testTag6() throws Exception {
    HtmlTag tag = new HtmlTag("a href=\"something\"/");
    assertEquals("a", tag.getName());
    assertEquals("something", tag.get("href"));
    assertTrue(tag.is(HtmlTag.Type.COMPLETE));
  }

  @Test
  public void testTag7() throws Exception {
    HtmlTag tag = new HtmlTag("<a href=\"something\">");
    assertEquals("a", tag.getName());
    assertEquals("something", tag.get("href"));
    assertTrue(tag.is(HtmlTag.Type.OPEN));
  }
}
