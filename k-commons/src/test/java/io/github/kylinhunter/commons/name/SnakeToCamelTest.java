package io.github.kylinhunter.commons.name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;

class SnakeToCamelTest {

    @Test
    void convert() {
        SnakeToCamel snakeToCamel = CF.get(SnakeToCamel.class);

        String result = snakeToCamel.convert("HELLO_WORD");
        System.out.println(result);
        Assertions.assertEquals("helloWord", result);

        result = snakeToCamel.convert("HELLO_WORD", CamelFormat.LOWER);
        System.out.println(result);
        Assertions.assertEquals("helloWord", result);

        result = snakeToCamel.convert("H_W", CamelFormat.LOWER);
        System.out.println(result);
        Assertions.assertEquals("hW", result);

        result = snakeToCamel.convert("hello_word", CamelFormat.UPPER);
        System.out.println(result);
        Assertions.assertEquals("HelloWord", result);

        result = snakeToCamel.convert("h_w", CamelFormat.UPPER);
        System.out.println(result);
        Assertions.assertEquals("HW", result);

        result = snakeToCamel.convert("helloWord", CamelFormat.UPPER);
        System.out.println(result);
        Assertions.assertEquals("helloWord", result);

        result = snakeToCamel.convert("HelloWord", CamelFormat.LOWER);
        System.out.println(result);
        Assertions.assertEquals("HelloWord", result);
    }
}