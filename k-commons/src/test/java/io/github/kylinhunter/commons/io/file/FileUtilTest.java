package io.github.kylinhunter.commons.io.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileUtilTest {
    @Test
    void test() {
        /*
        ln ./test1.txt ./test1_hard_link.txt
        ln -s  ./test1.txt ./test1_soft_link.txt
        ln -s ./test1Dir ./test1Dir_soft_link
         */
        print("/Users/bijian/tmp/test1.txt");

        print("/Users/bijian/tmp/test1_hard_link.txt");

        print("/Users/bijian/tmp/test1_soft_link.txt");

        print("/Users/bijian/tmp/test1Dir");

        print("/Users/bijian/tmp/test1Dir_soft_link");

    }

    void print(String pathStr) {
        Path path = Paths.get(pathStr);
        System.out.println("===");
        System.out.println(path);
        System.out.println(Files.exists(path)+":"+Files.exists(path, LinkOption.NOFOLLOW_LINKS));
        System.out.println(Files.isDirectory(path)+":"+Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS));

    }

    @Test
    void cleanDirectoryQuietly() throws IOException {
        File tmpDir = UserDirUtils.getTmpDir("io-test");
        FileUtils.cleanDirectory(tmpDir);
        Assertions.assertTrue(FileUtils.isEmptyDirectory(tmpDir));
        File file1 = UserDirUtils.getTmpFile("io-test/test1/" + UUID.randomUUID().toString(), true, true);
        System.out.println(file1.getAbsolutePath());
        File file2 = UserDirUtils.getTmpFile("io-test/test2/" + UUID.randomUUID().toString(), true, true);
        System.out.println(file2.getAbsolutePath());
        File file3 = UserDirUtils.getTmpFile("io-test/test2/" + UUID.randomUUID().toString(), true, true);
        System.out.println(file3.getAbsolutePath());
        Collection<File> files = FileUtils.listFiles(tmpDir, null, true);
        files.forEach(System.out::println);
        Assertions.assertEquals(3, files.size());
        Assertions.assertFalse(FileUtils.isEmptyDirectory(tmpDir));
        FileUtil.cleanDirectoryQuietly(tmpDir);
        Assertions.assertTrue(FileUtils.isEmptyDirectory(tmpDir));
    }
}