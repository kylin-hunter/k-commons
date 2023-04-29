package io.github.kylinhunter.commons.io.file;

import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserDirUtilsTest {
    private static final String SEP = File.separator;
    private static final String TEST_DIR = "tmp" + SEP + "user-dir-test";
    private static final File BASE_DIR = new File(System.getProperty("user.dir"), TEST_DIR);

    private static final String DIR1 = "dir1";
    private static final String DIR2 = "dir2";
    private static final String DIR3 = "dir3";

    @BeforeAll
    static void beforeAll() {
        FileUtil.cleanDirectoryQuietly(BASE_DIR);
        System.out.println("clean:" + BASE_DIR.getAbsolutePath());
    }

    @Test
    void testGetFile() {
        File file1 = UserDirUtils.getFile(TEST_DIR + SEP + DIR1 + SEP + "file1.txt", false);
        System.out.println("file1:" + file1.getAbsolutePath());
        Assertions.assertFalse(file1.getParentFile().exists());
        Assertions.assertFalse(file1.exists());

        File file2 = UserDirUtils.getFile(TEST_DIR + SEP + DIR1 + SEP + "file2.txt");
        System.out.println("file2:" + file2.getAbsolutePath());
        Assertions.assertTrue(file2.getParentFile().exists());
        Assertions.assertFalse(file2.exists());

        File file3 = UserDirUtils.getFile(TEST_DIR + SEP + DIR1 + SEP + "file3.txt", true, true);
        System.out.println("file3:" + file3.getAbsolutePath());
        Assertions.assertTrue(file3.getParentFile().exists());
        Assertions.assertTrue(file3.exists());

    }

    @Test
    void testGetDir() {
        File dir2 = UserDirUtils.getDir(TEST_DIR + SEP + DIR2, false);
        System.out.println("dir2:" + dir2.getAbsolutePath());
        Assertions.assertFalse(dir2.exists());

        File dir3 = UserDirUtils.getDir(TEST_DIR + SEP + DIR3);
        System.out.println("dir3:" + dir3.getAbsolutePath());
        Assertions.assertTrue(dir3.getParentFile().exists());

    }

}