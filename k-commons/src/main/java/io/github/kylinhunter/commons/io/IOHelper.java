package io.github.kylinhunter.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import org.apache.commons.io.IOUtils;

import io.github.kylinhunter.commons.exception.embed.InitException;
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
     * @date 2023-02-14 01:08
     */
    public static String toString(InputStream input, String charsetName) {

        try {
            if (input == null) {
                throw new InitException("input can't be null");
            }
            return IOUtils.toString(input, charsetName);
        } catch (IOException e) {
            throw new KIOException("toString error", e);
        }
    }
}
