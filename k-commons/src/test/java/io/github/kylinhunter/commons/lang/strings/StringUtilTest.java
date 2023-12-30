package io.github.kylinhunter.commons.lang.strings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.Test;

class StringUtilTest {

  @Test
  void test() {

    assertTrue(StringUtil.isEmpty(""));
    assertTrue(StringUtil.isBlank(" "));
    assertEquals("a", StringUtil.defaultIfBlank(" ", "a"));

    assertEquals("a", StringUtil.defaultIfBlank("", "a"));
    assertEquals(3, StringUtil.length("123"));

    assertEquals("a", StringUtil.defaultString(null, "a"));
    assertEquals("", StringUtil.defaultString(null));
  }

  @Test
  void split() {
    String str = "a.b.c.d.";
    assertEquals(4, StringUtil.split(str, '.').length);
  }

  @Test
  void split2() {
    String str = "a=b&b=c&c=d&d=";
    Map<String, String> result = StringUtil.split(str, '&', '=');
    result.forEach((k, v) -> {
      System.out.println(k + ":" + v);
    });
    assertEquals(3, result.size());
  }
}
