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
        File file = ResourceHelper.getFile("test/test.txt");
        String md5 = MD5Utils.md5(file);
        System.out.println(md5);
        Assertions.assertEquals("098f6bcd4621d373cade4e832627b4f6", md5);
    }
}