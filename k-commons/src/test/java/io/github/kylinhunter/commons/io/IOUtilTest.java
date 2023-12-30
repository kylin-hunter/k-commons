package io.github.kylinhunter.commons.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IOUtilTest {

  @Test
  void testToStringByteArray() {
    String str = "你好吗中国";
    byte[] bytes = str.getBytes(Charsets.UTF_8);
    String result = IOUtil.toString(bytes, Charsets.UTF_8, 0);
    Assertions.assertEquals(str, result);

    result = IOUtil.toString(bytes, Charsets.UTF_8, -1);
    Assertions.assertEquals(str, result);

    result = IOUtil.toString(bytes, Charsets.UTF_8, 2);
    Assertions.assertEquals("你好...", result);
    result = IOUtil.toString(bytes, Charsets.UTF_8, 3);
    Assertions.assertEquals("你好吗...", result);

    result = IOUtil.toString(bytes, Charsets.UTF_8, 4);
    Assertions.assertEquals("你好吗中...", result);

    result = IOUtil.toString(bytes, Charsets.UTF_8, 5);
    Assertions.assertEquals("你好吗中国", result);

    result = IOUtil.toString(bytes, Charsets.UTF_8, 14);
    Assertions.assertEquals("你好吗中国", result);
  }

  @Test
  void testToStringInputStream() throws IOException {
    String str = "你好吗中国";
    byte[] bytes = str.getBytes(Charsets.UTF_8);

    String result = IOUtil.toString(new ByteArrayInputStream(bytes), Charsets.UTF_8, 0);
    Assertions.assertEquals(str, result);

    result = IOUtil.toString(new ByteArrayInputStream(bytes), Charsets.UTF_8, -1);
    Assertions.assertEquals(str, result);

    result = IOUtil.toString(new ByteArrayInputStream(bytes), Charsets.UTF_8, 2);
    Assertions.assertEquals("你好...", result);
    result = IOUtil.toString(new ByteArrayInputStream(bytes), Charsets.UTF_8, 3);
    Assertions.assertEquals("你好吗...", result);

    result = IOUtil.toString(new ByteArrayInputStream(bytes), Charsets.UTF_8, 4);
    Assertions.assertEquals("你好吗中...", result);

    result = IOUtil.toString(new ByteArrayInputStream(bytes), Charsets.UTF_8, 5);
    Assertions.assertEquals("你好吗中国", result);

    result = IOUtil.toString(new ByteArrayInputStream(bytes), Charsets.UTF_8, 14);
    Assertions.assertEquals("你好吗中国", result);
  }
}