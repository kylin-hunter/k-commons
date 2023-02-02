package io.github.kylinhunter.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ResourceHelperTest {

    @Test
    void getFile() {
        String path = "/test/file/test1.txt";
        File file = ResourceHelper.getFile(path);
        Assertions.assertNotNull(file);

        path = "classpath:/test/file/test1.txt";
        file = ResourceHelper.getFile(path);
        Assertions.assertNotNull(file);

        path = "$user.dir$/src/test/resources/test/file/test1.txt";
        file = ResourceHelper.getFile(path);
        Assertions.assertNotNull(file);

        path = "file://" + file.getAbsolutePath();
        file = ResourceHelper.getFile(path);
        Assertions.assertNotNull(file);
        //==
        String pathErr = "file://" + file.getParent();
        Assertions.assertNull(ResourceHelper.getFile(pathErr));


        path = "org/apache/commons/lang3/StringUtils.class";
        file = ResourceHelper.getFile(path);
        Assertions.assertNull(file);

        file = ResourceHelper.getFile(path + "1");
        Assertions.assertNull(file);

    }

    @Test
    void getFileInClassPath() {

        String path = "/test/file/test1.txt";
        File file = ResourceHelper.getFileInClassPath(path);
        Assertions.assertNotNull(file);

        file = ResourceHelper.getFileInClassPath(path + "1");
        Assertions.assertNull(file);

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

        //==
        path = "org/apache/commons/lang3/StringUtils.class";
        dir = ResourceHelper.getDir(path);
        Assertions.assertNull(dir);

        dir = ResourceHelper.getDir(path + "1");
        Assertions.assertNull(dir);

        dir = ResourceHelper.getDir("file://" + file.getAbsolutePath()+"1");
        Assertions.assertNull(dir);
    }

    @Test
    void getDirInClassPath() {

        String path = "/test/file";
        File file = ResourceHelper.getDirInClassPath(path);
        Assertions.assertNotNull(file);

        file = ResourceHelper.getDirInClassPath(path + "1");
        Assertions.assertNull(file);

    }

    @Test
    void getInputStream() throws IOException {

        String path = "/test/file/test1.txt";
        try (InputStream input = ResourceHelper.getInputStream(path)) {
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
        //==
        path = "org/apache/commons/lang3/StringUtils.class";
        try (InputStream input = ResourceHelper.getInputStreamInClassPath(path)) {
            Assertions.assertNotNull(input);
        }
        try (InputStream input = ResourceHelper.getInputStreamInClassPath(path + "1")) {
            Assertions.assertNull(input);
        }
    }

}