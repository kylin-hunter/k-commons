package com.kylinhunter.plat.commons.codec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-22 03:19
 **/
public class Base64Util {

    public static String toString(byte[] bytes) {
        byte[] base64Bytes = Base64.getEncoder().encode(bytes);
        return new String(base64Bytes, StandardCharsets.UTF_8);
    }

    public static byte[] toBytes(String text) {
        byte[] keyBytes = text.getBytes(StandardCharsets.UTF_8);
        return Base64.getDecoder().decode(keyBytes);
    }
}
