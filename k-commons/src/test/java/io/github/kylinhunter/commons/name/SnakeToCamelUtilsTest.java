package io.github.kylinhunter.commons.name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;

class SnakeToCamelUtilsTest {

    @Test
    void convert() {

        String result = SnakeToCamelUtils.convert("HELLO_WORD");
        System.out.println(result);
        Assertions.assertEquals("helloWord", result);

        result = SnakeToCamelUtils.convert("HELLO_WORD", CamelFormat.LOWER);
        System.out.println(result);
        Assertions.assertEquals("helloWord", result);

        result = SnakeToCamelUtils.convert("H_W", CamelFormat.LOWER);
        System.out.println(result);
        Assertions.assertEquals("hW", result);

        result = SnakeToCamelUtils.convert("hello_word", CamelFormat.UPPER);
        System.out.println(result);
        Assertions.assertEquals("HelloWord", result);

        result = SnakeToCamelUtils.convert("h_w", CamelFormat.UPPER);
        System.out.println(result);
        Assertions.assertEquals("HW", result);

        result = SnakeToCamelUtils.convert("helloWord", CamelFormat.UPPER);
        System.out.println(result);
        Assertions.assertEquals("HelloWord", result);

        result = SnakeToCamelUtils.convert("HelloWord", CamelFormat.LOWER);
        System.out.println(result);
        Assertions.assertEquals("helloWord", result);
    }
}