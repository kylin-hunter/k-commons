package io.github.kylinhunter.commons.io;

import io.github.kylinhunter.commons.exception.embed.KIOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URI;
import java.nio.charset.Charset;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-24 22:17
 **/
public class IOHelper implements Serializable {

    /**
     * The Windows separator character.
     */
    private static final char WINDOWS_SEPARATOR = '\\';

    /**
     * The system separator character.
     */
    private static final char SYSTEM_SEPARATOR = File.separatorChar;

    /**
     * @return boolean
     * @title isSystemWindows
     * @description
     * @author BiJi'an
     * @date 2023-04-22 19:51
     */
    public static boolean isSystemWindows() {
        return SYSTEM_SEPARATOR == WINDOWS_SEPARATOR;
    }

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
     * @param input input
     * @return java.lang.String
     * @title toString
     * @description
     * @author BiJi'an
     * @date 2023-03-04 01:15
     */
    public static String toString(InputStream input) {
        return toString(input, Charset.defaultCharset());
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
        } catch (IOException e) {
            throw new KIOException("toString error", e);
        }

    }

    /**
     * @param file file
     * @return java.io.FileInputStream
     * @title newFileInputStream
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:16
     */
    public static FileInputStream getFileInputStream(File file) {
        try {
            if (file != null && file.exists() && file.isFile()) {
                return new FileInputStream(file);
            }
        } catch (Exception e) {
            // ignore
        }
        return null;
    }
}
