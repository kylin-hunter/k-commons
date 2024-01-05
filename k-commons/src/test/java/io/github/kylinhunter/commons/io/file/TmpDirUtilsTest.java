package io.github.kylinhunter.commons.io.file;

import java.io.File;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TmpDirUtilsTest {

  private static final String SEP = File.separator;
  private static final String TEST_DIR = "TmpDirUtilsTest";
  private static final File BASE_DIR1 = TmpDirUtils.getUserDir(TEST_DIR);
  private static final File BASE_DIR2 = TmpDirUtils.getSysDir(TEST_DIR);


  private static final String DIR1 = "dir1";
  private static final String DIR2 = "dir2";
  private static final String DIR3 = "dir3";

  @BeforeAll
  static void beforeAll() {
    FileUtil.cleanDirectoryQuietly(BASE_DIR1);
    System.out.println("clean:" + BASE_DIR1.getAbsolutePath());
    FileUtil.cleanDirectoryQuietly(BASE_DIR2);
    System.out.println("clean:" + BASE_DIR2.getAbsolutePath());
  }

  @AfterAll
  static void afterAll() {
    FileUtil.deleteQuietly(BASE_DIR1);
    FileUtil.deleteQuietly(BASE_DIR2);
  }

  @Test
  void testGetFile() {
    File file1 = TmpDirUtils.getUserFile(false, TEST_DIR + SEP + DIR1 + SEP + "file1.txt");
    System.out.println("file1:" + file1.getAbsolutePath());
    Assertions.assertFalse(file1.getParentFile().exists());
    Assertions.assertFalse(file1.exists());

    file1 = TmpDirUtils.getSysFile(false, TEST_DIR + SEP + DIR1 + SEP + "file1.txt");
    System.out.println("file1:" + file1.getAbsolutePath());
    Assertions.assertFalse(file1.getParentFile().exists());
    Assertions.assertFalse(file1.exists());

    File file2 = TmpDirUtils.getUserFile(TEST_DIR + SEP + DIR1 + SEP + "file2.txt");
    System.out.println("file2:" + file2.getAbsolutePath());
    Assertions.assertTrue(file2.getParentFile().exists());
    Assertions.assertFalse(file2.exists());

    file2 = TmpDirUtils.getSysFile(TEST_DIR + SEP + DIR1 + SEP + "file2.txt");
    System.out.println("file2:" + file2.getAbsolutePath());
    Assertions.assertTrue(file2.getParentFile().exists());
    Assertions.assertFalse(file2.exists());

    File file3 = TmpDirUtils.getUserFile(true, true, TEST_DIR + SEP + DIR1 + SEP + "file3.txt");
    System.out.println("file3:" + file3.getAbsolutePath());
    Assertions.assertTrue(file3.getParentFile().exists());
    Assertions.assertTrue(file3.exists());

    file3 = TmpDirUtils.getSysFile(true, true, TEST_DIR + SEP + DIR1 + SEP + "file3.txt");
    System.out.println("file3:" + file3.getAbsolutePath());
    Assertions.assertTrue(file3.getParentFile().exists());
    Assertions.assertTrue(file3.exists());
  }

  @Test
  void testGetDir() {
    File dir2 = TmpDirUtils.getUserDir(false, TEST_DIR + SEP + DIR2);
    System.out.println("dir2:" + dir2.getAbsolutePath());
    Assertions.assertFalse(dir2.exists());

    dir2 = TmpDirUtils.getSysDir(false, TEST_DIR + SEP + DIR2);
    System.out.println("dir2:" + dir2.getAbsolutePath());
    Assertions.assertFalse(dir2.exists());

    File dir3 = TmpDirUtils.getUserDir(TEST_DIR + SEP + DIR3);
    System.out.println("dir3:" + dir3.getAbsolutePath());
    Assertions.assertTrue(dir3.exists());

    dir3 = TmpDirUtils.getSysDir(TEST_DIR + SEP + DIR3);
    System.out.println("dir3:" + dir3.getAbsolutePath());
    Assertions.assertTrue(dir3.exists());
  }
}
