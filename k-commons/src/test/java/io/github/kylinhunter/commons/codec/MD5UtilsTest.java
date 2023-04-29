package io.github.kylinhunter.commons.codec;

import io.github.kylinhunter.commons.io.ResourceHelper;
import java.io.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        String salt = "$1$kylinhunter";
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
        Assertions.assertEquals("bd25bce55cd3d9a3334e9b5231ad05d7", md5);
    }
}