package io.github.kylinhunter.commons.io.file.path;

import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.io.file.filter.SuffixPathFilter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PathUtilTest {

  static String base_dir = "path-util-test";
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

  @Test
  void walk() {
    File file1 = FileUtil.getFile(dir1, true, true, "test11.txt1");
    System.out.println(file1.getAbsolutePath());
    Assertions.assertTrue(Files.exists(file1.toPath()));

    File file2 = FileUtil.getFile(dir1, true, true, "test11.txt2");
    System.out.println(file2.getAbsolutePath());
    Assertions.assertTrue(Files.exists(file2.toPath()));

    File file3 = FileUtil.getFile(dir1, true, true, "test11.txt3");
    System.out.println(file3.getAbsolutePath());
    Assertions.assertTrue(Files.exists(file3.toPath()));

    Stream<Path> walk =
        PathUtil.walk(
            baseDir.toPath(),
            new SuffixPathFilter("txt1").or(new SuffixPathFilter("txt2")),
            Integer.MAX_VALUE,
            false);
    walk.forEach(
        path -> {
          System.out.println(path);
        });
  }
}
