package io.github.kylinhunter.commons.io;

import java.nio.charset.Charset;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:59
 **/
public class Charsets {

    /**
     * @param charsetName charsetName
     * @return java.nio.charset.Charset
     * @title toCharset
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:59
     */
    public static Charset toCharset(final String charsetName) {
        return charsetName == null ? Charset.defaultCharset() : Charset.forName(charsetName);
    }
}
