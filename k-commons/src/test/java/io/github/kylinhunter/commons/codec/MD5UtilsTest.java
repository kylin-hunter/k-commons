package io.github.kylinhunter.commons.codec;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.io.ResourceHelper;

class MD5UtilsTest {

    @Test
    public void testMd5() {
        String text = "bijian";
        String md5 = MD5Utils.md5(text);
        System.out.println(md5);
        md5 = MD5Utils.md5(text);
        System.out.println(md5); // 可以破解的 ，解密网站 https://www.cmd5.com/
        Assertions.assertEquals("88406dca3994c8fcc82f065fa68f9f41", md5);

    }

    @Test
    void testMd5WithSalt() {
        String text = "bijian";
        String salt = "$1$salt";
        System.out.println("salt:" + salt);
        String md5 = MD5Utils.md5(text, salt);
        System.out.println(md5);
        md5 = MD5Utils.md5(text, salt);
        System.out.println(md5); //

    }

    @Test
    void md5File() {
        File file = ResourceHelper.getFile("test/file/test1.txt");
        Assertions.assertNotNull(file);
        String md5 = MD5Utils.md5(file);
        System.out.println(md5);
        Assertions.assertEquals("5a105e8b9d40e1329780d62ea2265d8a", md5);
    }
}