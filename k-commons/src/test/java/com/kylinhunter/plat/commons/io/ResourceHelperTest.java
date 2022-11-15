package com.kylinhunter.plat.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kylinhunter.plat.commons.io.file.UserDirUtils;

class ResourceHelperTest {

    @Test
    void getFile() {

        File classPathFile = UserDirUtils.getFile("/build/resources/test/test/test.txt", false);

        String path = "classpath:/test/test.txt";
        File file = ResourceHelper.getFile(path);
        Assertions.assertEquals(classPathFile.getAbsolutePath(), file.getAbsolutePath());


        path = "classpath:org/apache/commons/lang3/StringUtils.class";
        file = ResourceHelper.getFile(path);
        Assertions.assertNull(file);

        path = "classpath:test/test1.txt";
        file = ResourceHelper.getFile(path);
        Assertions.assertNull(file);

        path = "$user.dir$/src/test/resources/test/test.txt";
        classPathFile = UserDirUtils.getFile("src/test/resources/test/test.txt", false);
        file = ResourceHelper.getFile(path);
        Assertions.assertEquals(classPathFile.getAbsolutePath(), file.getAbsolutePath());

        path = "$user.dir$/src/test/resources/test/test1.txt";
        file = ResourceHelper.getFile(path);
        Assertions.assertNull(file);
    }

    @Test
    void getInputStream() throws IOException {


        String path = "classpath:/test/test.txt";
        InputStream input = ResourceHelper.getInputStream(path);
        input.close();
        Assertions.assertNotNull(input);


        path = "classpath:org/apache/commons/lang3/StringUtils.class";
        input = ResourceHelper.getInputStream(path);
        Assertions.assertNotNull(input);


        path = "classpath:test/test1.txt";
        input = ResourceHelper.getInputStream(path);
        Assertions.assertNull(input);


        path = "$user.dir$/src/test/resources/test/test.txt";
        input = ResourceHelper.getInputStream(path);
        input.close();
        Assertions.assertNotNull(input);

        path = "$user.dir$/src/test/resources/test/test1.txt";
        input = ResourceHelper.getInputStream(path);
        Assertions.assertNull(input);
    }

}