package io.github.kylinhunter.commons.utils.compress;

import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.io.file.reader.FileReaderUtils;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ZipUtilsTest {

  @Test
  @Order(1)
  public void testZip() throws IOException {

    File dir = ResourceHelper.getDirInClassPath("test/file");
    File[] files = FileUtil.listFiles(dir, null, true).toArray(new File[0]);
    List<File> fileList = Arrays.stream(files).collect(Collectors.toList());
    File file = UserDirUtils.getFile(false, "/tmp/test_unzip/testzip.zip");
    if (file.exists()) {
      FileUtil.delete(file);
    }
    ZipUtils.zip(fileList, dir, file);

    Assertions.assertTrue(file.length() > 0);
  }

  @Test
  @Order(2)
  public void testUnZip() throws IOException {
    File file = UserDirUtils.getFile(false, "/tmp/test_unzip/testzip.zip");
    File dir1 = UserDirUtils.getDir(true, "/tmp/test_unzip/testzip1");
    FileUtil.forceDelete(dir1);
    ZipUtils.unzip(file, dir1);
    Collection<File> files1 = FileUtil.listFiles(dir1, null, true);

    File dir2 = UserDirUtils.getDir(true, "/tmp/test_unzip/testzip2");
    FileUtil.forceDelete(dir2);
    ZipUtils.unzip(FileReaderUtils.readFileToByteArray(file), dir2);
    Collection<File> files2 = FileUtil.listFiles(dir2, null, true);

    Assertions.assertEquals(4, files1.size());
    Assertions.assertEquals(4, files2.size());
  }
}
