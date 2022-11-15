package com.kylinhunter.plat.commons.codec;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;

import com.kylinhunter.plat.commons.exception.inner.KIOException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-07 00:47
 **/
public class MD5Util {

    public static String md5(String text) {
        return DigestUtils.md5Hex(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String md5(String text, String salt) {
        return Md5Crypt.md5Crypt(text.getBytes(StandardCharsets.UTF_8), salt);
    }

    public static String md5(byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }

    public static String md5(InputStream inputStream) {
        try {
            return DigestUtils.md2Hex(inputStream);

        } catch (Exception e) {
            throw new KIOException("md5 error ", e);
        }

    }

    public static String md5(File file) {
        if (!file.isFile()) {
            throw new KIOException("not file " + file.getAbsolutePath());
        }
        try (FileInputStream in = new FileInputStream(file)) {
            return DigestUtils.md5Hex(in);
        } catch (Exception e) {
            throw new KIOException("md5 error ", e);

        }

    }
}
