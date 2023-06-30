package io.github.kylinhunter.commons.io;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.io.ResourceHelper.PathType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResourceHelperTest {

  @Test
  void getFile() {
    String path = "/test/file/test1.txt";
    File file = ResourceHelper.getFile(path);
    Assertions.assertNotNull(file);

    file = ResourceHelper.getFile(file.getAbsolutePath(), PathType.CLASSPATH, true);
    Assertions.assertNotNull(file);

    path = "classpath:/test/file/test1.txt";
    file = ResourceHelper.getFile(path);
    Assertions.assertNotNull(file);

    path = "$user.dir$/src/test/resources/test/file/test1.txt";
    file = ResourceHelper.getFile(path);
    Assertions.assertNotNull(file);

    path = "$user.dir$/src/test/resources/test/file/test1.txt";
    file = ResourceHelper.getFile(path);
    Assertions.assertNotNull(file);

    path = "file://" + file.getAbsolutePath();
    file = ResourceHelper.getFile(path);
    Assertions.assertNotNull(file);
    // ==
    String pathErr = "file://" + file.getParent();
    Assertions.assertNull(ResourceHelper.getFile(pathErr));
    Assertions.assertThrows(KRuntimeException.class, () -> ResourceHelper.getFile(pathErr, true));

    path = "org/apache/commons/lang3/StringUtils.class";
    file = ResourceHelper.getFile(path);
    Assertions.assertNull(file);

    file = ResourceHelper.getFile(path + "1");
    Assertions.assertNull(file);

    Assertions.assertThrows(KRuntimeException.class, () -> ResourceHelper.getFile("1", true));
  }

  @Test
  void getFileInClassPath() {

    String path = "/test/file/test1.txt";
    File file = ResourceHelper.getFileInClassPath(path);
    Assertions.assertNotNull(file);

    file = ResourceHelper.getFileInClassPath(path + "1");
    Assertions.assertNull(file);

    file = ResourceHelper.getFileInClassPath(path + "1", false);
    Assertions.assertNull(file);

    Assertions.assertThrows(
        KRuntimeException.class, () -> ResourceHelper.getFileInClassPath("1", true));
  }

  @Test
  void getDir() {
    String path = "/test/file/test1.txt";
    File file = ResourceHelper.getFile(path);

    path = "/test/file";
    File dir = ResourceHelper.getDir(path);
    Assertions.assertNotNull(dir);

    path = "classpath:/test/file";
    dir = ResourceHelper.getDir(path);
    Assertions.assertNotNull(dir);

    path = "$user.dir$/src/test/resources/test/file";
    dir = ResourceHelper.getDir(path);
    Assertions.assertNotNull(dir);

    path = "file://" + dir.getAbsolutePath();
    dir = ResourceHelper.getDir(path);
    Assertions.assertNotNull(dir);

    String pathErr = "file://" + file.getAbsolutePath();
    Assertions.assertNull(ResourceHelper.getDir(pathErr));

    Assertions.assertThrows(KRuntimeException.class, () -> ResourceHelper.getDir(pathErr, true));

    // ==
    path = "org/apache/commons/lang3/StringUtils.class";
    dir = ResourceHelper.getDir(path);
    Assertions.assertNull(dir);

    dir = ResourceHelper.getDir(path + "1");
    Assertions.assertNull(dir);

    dir = ResourceHelper.getDir("file://" + file.getAbsolutePath() + "1");
    Assertions.assertNull(dir);
  }

  @Test
  void getDirInClassPath() {

    String path = "/test/file";
    File file = ResourceHelper.getDirInClassPath(path);
    Assertions.assertNotNull(file);

    file = ResourceHelper.getDirInClassPath(path + "1");
    Assertions.assertNull(file);

    file = ResourceHelper.getDirInClassPath(path + "1", false);
    Assertions.assertNull(file);

    Assertions.assertThrows(
        KRuntimeException.class, () -> ResourceHelper.getDirInClassPath("1", true));
  }

  @Test
  void getInputStream() throws IOException {

    String path = "/test/file/test1.txt";
    try (InputStream input = ResourceHelper.getInputStream(path)) {
      Assertions.assertNotNull(input);
    }

    try (InputStream input = ResourceHelper.getInputStream(path, PathType.CLASSPATH)) {
      Assertions.assertNotNull(input);
    }

    path = "classpath:/test/file/test1.txt";
    try (InputStream input = ResourceHelper.getInputStream(path)) {
      Assertions.assertNotNull(input);
    }
    path = "$user.dir$/src/test/resources/test/file/test1.txt";
    try (InputStream input = ResourceHelper.getInputStream(path)) {
      Assertions.assertNotNull(input);
    }

    Assertions.assertThrows(
        KRuntimeException.class,
        () -> {
          try (InputStream input = ResourceHelper.getInputStream("1", true)) {
            System.out.println(input);
          }
        });

    // ==
    path = "org/apache/commons/lang3/StringUtils.class";
    try (InputStream input = ResourceHelper.getInputStreamInClassPath(path)) {
      Assertions.assertNotNull(input);
    }
    try (InputStream input = ResourceHelper.getInputStreamInClassPath(path + "1")) {
      Assertions.assertNull(input);
    }

    try (InputStream input = ResourceHelper.getInputStreamInClassPath(path + "1")) {
      Assertions.assertNull(input);
    }
  }

  @Test
  void getText() {

    String path = "/test/file/test1.txt";
    String text1 = ResourceHelper.getText(path);
    System.out.println(text1);

    String text11 = ResourceHelper.getText(path, Charset.defaultCharset());
    System.out.println(text11);
    Assertions.assertEquals(text1, text11);

    String text2 = ResourceHelper.getText(path, PathType.CLASSPATH);
    System.out.println(text2);
    Assertions.assertEquals(text1, text2);

    String text3 = ResourceHelper.getText(path, PathType.CLASSPATH, StandardCharsets.UTF_8);
    System.out.println(text3);

    Assertions.assertEquals(text1, text3);

    String text4 = ResourceHelper.getText(path, PathType.CLASSPATH, StandardCharsets.ISO_8859_1);
    System.out.println(text4);
    Assertions.assertNotEquals(text1, text4);
  }
}
