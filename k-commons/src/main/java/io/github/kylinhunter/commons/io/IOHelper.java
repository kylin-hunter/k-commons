package io.github.kylinhunter.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.embed.KIOException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-24 22:17
 **/
public class IOHelper {

    /**
     * @param uri uri
     * @return java.net.URI
     * @title toURI
     * @description
     * @author BiJi'an
     * @date 2023-01-24 22:19
     */
    public static URI toURI(String uri) {
        try {
            return new URI(uri);
        } catch (Exception e) {
            throw new KIOException("invalid uri:" + uri);
        }
    }

    /**
     * @param input       input
     * @param charsetName charsetName
     * @return java.lang.String
     * @title toString
     * @description
     * @author BiJi'an
     * @date 2023-02-12 01:08
     */
    public static String toString(InputStream input, String charsetName) {
        return toString(input, Charsets.toCharset(charsetName));
    }

    /**
     * @param input       input
     * @param charsetName charsetName
     * @return java.lang.String
     * @title toString
     * @description
     * @author BiJi'an
     * @date 2023-02-26 10:35
     */
    public static String toString(InputStream input, Charset charsetName) {

        try {
            if (input == null) {
                throw new KIOException("InputStream can't be null");
            }
            return IOUtils.toString(input, charsetName);
        } catch (KRuntimeException e) {
            throw e;
        } catch (IOException e) {
            throw new KIOException("toString error", e);
        }

    }
}
