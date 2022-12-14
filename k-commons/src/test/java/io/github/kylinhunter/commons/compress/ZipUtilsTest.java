package io.github.kylinhunter.commons.compress;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.UserDirUtils;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ZipUtilsTest {

    @Test
    @Order(1)
    public void testZip() throws IOException {

        File dir = ResourceHelper.getDirInClassPath("test/file");
        File[] files = FileUtils.listFiles(dir, null, true).toArray(new File[0]);
        List<File> fileList = Arrays.stream(files).collect(Collectors.toList());
        File file = UserDirUtils.getFile("/tmp/test_unzip/testzip.zip", false);
        if (file.exists()) {
            FileUtils.delete(file);
        }
        ZipUtils.zip(fileList, file);
    }

    @Test
    @Order(2)
    public void testUnZip() throws IOException {
        File file = UserDirUtils.getFile("/tmp/test_unzip/testzip.zip", false);
        File dir1 = UserDirUtils.getDir("/tmp/test_unzip/testzip1", true);
        FileUtils.forceDelete(dir1);
        ZipUtils.unzip(file, dir1);


        File dir2 = UserDirUtils.getDir("/tmp/test_unzip/testzip2", true);
        FileUtils.forceDelete(dir2);
        ZipUtils.unzip(FileUtils.readFileToByteArray(file), dir2);
    }
}