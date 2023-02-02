package io.github.kylinhunter.commons.io;

import java.net.URI;

import io.github.kylinhunter.commons.exception.embed.KIOException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-24 22:17
 **/
public class IOHelper {

    /**
     * @title toURI
     * @description
     * @author BiJi'an
     * @param uri uri
     * @date 2023-01-24 22:19
     * @return java.net.URI
     */
    public static URI toURI(String uri) {
        try {
            return new URI(uri);
        } catch (Exception e) {
            throw new KIOException("invalid uri:" + uri);
        }
    }
}
