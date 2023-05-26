package io.github.kylinhunter.commons.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.UUID;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FileUtilTest {

  static String base_dir = "file-util-test";
  static File baseDir = UserDirUtils.getTmpDir(base_dir);
  static File dir1;
  static File dir2;
  static File dir3;

  @BeforeAll
  static void beforeAll() {
    FileUtil.cleanDirectoryQuietly(baseDir);
    dir1 = UserDirUtils.getTmpDir(base_dir + File.separator + "dir1");
    dir2 = UserDirUtils.getTmpDir(base_dir + File.separator + "dir2");
    dir3 = UserDirUtils.getTmpDir(base_dir + File.separator + "dir3");
    System.out.println(dir1.getAbsolutePath());
    System.out.println(dir2.getAbsolutePath());
    System.out.println(dir3.getAbsolutePath());
  }

  @AfterAll
  static void afterAll() {
  }

  @Test
  void getFile() throws IOException {
    File file = FileUtil.getFile(dir2.getAbsolutePath(), "test11.txt");
    System.out.println(file.getAbsolutePath());
    Assertions.assertFalse(Files.exists(file.toPath()));

    file = FileUtil.getFile(true, dir2.getAbsolutePath(), "test12.txt");
    System.out.println(file.getAbsolutePath());
    Assertions.assertFalse(Files.exists(file.toPath()));

    file = FileUtil.getFile(true, true, dir2.getAbsolutePath(), "test13.txt");
    System.out.println(file.getAbsolutePath());
    Assertions.assertTrue(Files.exists(file.toPath()));

    file = FileUtil.getFile(dir2, "test21.txt");
    System.out.println(file.getAbsolutePath());
    Assertions.assertFalse(Files.exists(file.toPath()));

    file = FileUtil.getFile(dir2, true, "test22.txt");
    System.out.println(file.getAbsolutePath());
    Assertions.assertFalse(Files.exists(file.toPath()));

    file = FileUtil.getFile(dir2, true, true, "test23.txt");
    System.out.println(file.getAbsolutePath());
    Assertions.assertTrue(Files.exists(file.toPath()));
  }

  @Test
  void cleanDirectoryQuietly() {
    Assertions.assertTrue(FileUtil.isEmptyDirectory(dir1));
    File file1 =
        UserDirUtils.getTmpFile(true, true,
            base_dir + "/dir1/test1/" + UUID.randomUUID().toString());
    System.out.println(file1.getAbsolutePath());
    File file2 =
        UserDirUtils.getTmpFile(true, true,
            base_dir + "/dir1/test2/" + UUID.randomUUID().toString());
    System.out.println(file2.getAbsolutePath());
    File file3 =
        UserDirUtils.getTmpFile(true, true,
            base_dir + "/dir1/test2/" + UUID.randomUUID().toString());
    System.out.println(file3.getAbsolutePath());
    Collection<File> files = FileUtil.listFiles(dir1, null, true);
    files.forEach(System.out::println);
    Assertions.assertEquals(3, files.size());
    Assertions.assertFalse(FileUtil.isEmptyDirectory(dir1));
    FileUtil.cleanDirectoryQuietly(dir1);
    Assertions.assertTrue(FileUtil.isEmptyDirectory(dir1));
  }

  @Test
  void listFiles() {

    File file = FileUtil.getFile(dir3, true, true, "test1.txt");
    System.out.println(file.getAbsolutePath());
    file = FileUtil.getFile(dir3, true, true, "test2.jpg");
    System.out.println(file.getAbsolutePath());

    file = FileUtil.getFile(dir3, true, true, "test3.doc");

    file = FileUtil.getFile(dir3, true, true, "child", "test3.doc");
    System.out.println(file.getAbsolutePath());
    Assertions.assertEquals(3, FileUtil.listFiles(dir3, null, false).size());
    Assertions.assertEquals(4, FileUtil.listFiles(dir3, null, true).size());

    System.out.println(file.getAbsolutePath());
    Assertions.assertEquals(3, FileUtil.listFiles(dir3, new String[]{"doc", "txt"}, true).size());
  }
}
