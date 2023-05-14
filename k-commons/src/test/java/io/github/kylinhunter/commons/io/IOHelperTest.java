package io.github.kylinhunter.commons.io;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;

class IOHelperTest {

  @Test
  void testToString() throws IOException {
    String path = "/test/file/test1.txt";
    try (InputStream inputStream = ResourceHelper.getInputStream(path)) {
      String s = IOHelper.toString(inputStream);
      System.out.println(s);
    }
  }
}
