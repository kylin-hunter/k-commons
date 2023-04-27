package io.github.kylinhunter.commons.codec;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.kylinhunter.commons.exception.embed.CryptException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-07 00:47
 **/
public class MD5Utils {

    /**
     * @param text text
     * @return java.lang.String
     * @title md5
     * @description
     * @author BiJi'an
     * @date 2022-11-20 16:55
     */
    public static String md5(String text) {
        return md5(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param text text
     * @param salt salt
     * @return java.lang.String
     * @title md5
     * @description
     * @author BiJi'an
     * @date 2022-11-20 16:53
     */
    public static String md5(String text, String salt) {
        return Md5Crypt.md5Crypt(text.getBytes(StandardCharsets.UTF_8), salt);
    }

    /**
     * @param bytes bytes
     * @return java.lang.String
     * @title md5
     * @description
     * @author BiJi'an
     * @date 2022-11-20 16:53
     */
    @SuppressFBWarnings("WEAK_MESSAGE_DIGEST_MD5")
    public static String md5(byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }

    /**
     * @param inputStream inputStream
     * @return java.lang.String
     * @title md5
     * @description
     * @author BiJi'an
     * @date 2022-11-20 16:53
     */
    @SuppressFBWarnings("WEAK_MESSAGE_DIGEST_MD5")
    public static String md5(InputStream inputStream) {
        try {
            return DigestUtils.md5Hex(inputStream);

        } catch (Exception e) {
            throw new CryptException("md5 error ", e);
        }

    }

    /**
     * @param file file
     * @return java.lang.String
     * @title md5
     * @description
     * @author BiJi'an
     * @date 2022-11-20 16:53
     */
    public static String md5(File file) {
        if (!file.isFile()) {
            throw new CryptException("not file " + file.getAbsolutePath());
        }
        try (FileInputStream in = new FileInputStream(file)) {
            return md5(in);
        } catch (Exception e) {
            throw new CryptException("md5 error ", e);

        }

    }
}
