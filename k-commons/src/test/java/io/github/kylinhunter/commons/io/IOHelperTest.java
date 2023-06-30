package io.github.kylinhunter.commons.io;

import java.io.File;
import java.net.URI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IOHelperTest {

  @Test
  void testToString() {
    String path = "/test/file/test1.txt";
    File file = ResourceHelper.getFileInClassPath(path);
    URI uri = IOHelper.toURI(file.getAbsolutePath());
    System.out.println("uri:" + uri);
    Assertions.assertNotNull(uri);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> IOHelper.toURI(null));
  }
}
