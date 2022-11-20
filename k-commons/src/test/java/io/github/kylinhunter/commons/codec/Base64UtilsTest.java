package io.github.kylinhunter.commons.codec;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Base64UtilsTest {

    @Test
    void test() {
        String text = "bijian";
        String encodeText1 = Base64Utils.encodeToString(text, "UTF-8");
        System.out.println(encodeText1);

        String encodeText2 = Base64Utils.encodeToString(text, StandardCharsets.UTF_8);
        System.out.println(encodeText2);

        String encodeText3 = Base64Utils.encodeToString(text);
        System.out.println(encodeText3);

        Assertions.assertEquals(encodeText1, encodeText2);
        Assertions.assertEquals(encodeText2, encodeText3);

        String decodeText1 = Base64Utils.decodeToString(encodeText3);

        String decodeText2 = Base64Utils.decodeToString(encodeText3, "UTF-8");

        String decodeText3 = Base64Utils.decodeToString(encodeText3, StandardCharsets.UTF_8);

        Assertions.assertEquals(text, decodeText1);
        Assertions.assertEquals(text, decodeText2);
        Assertions.assertEquals(text, decodeText3);

    }

}