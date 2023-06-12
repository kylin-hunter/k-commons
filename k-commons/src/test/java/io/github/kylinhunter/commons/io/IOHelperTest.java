package io.github.kylinhunter.commons.io;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IOHelperTest {

  @Test
  void testToString() throws IOException {
    String path = "/test/file/test1.txt";
    File file = ResourceHelper.getFileInClassPath(path);
    URI uri = IOHelper.toURI(file.getAbsolutePath());
    System.out.println("uri:" + uri);
    Assertions.assertTrue(uri != null);
  }
}
