package io.github.kylinhunter.commons.name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NameUtilsTest {
    @Test
    public void test() {
        NamePair namePair = NameUtils.toNamePair("hello_word");
        System.out.println(namePair);
        Assertions.assertEquals("hello_word", namePair.getSnake());
        Assertions.assertEquals("helloWord", namePair.getCamel());

        namePair = NameUtils.toNamePair("h_w");
        System.out.println(namePair);
        Assertions.assertEquals("h_w", namePair.getSnake());
        Assertions.assertEquals("hW", namePair.getCamel());

        namePair = NameUtils.toNamePair("HELLO_WORD");
        System.out.println(namePair);
        Assertions.assertEquals("HELLO_WORD", namePair.getSnake());
        Assertions.assertEquals("helloWord", namePair.getCamel());

        namePair = NameUtils.toNamePair("H_W");
        System.out.println(namePair);
        Assertions.assertEquals("H_W", namePair.getSnake());
        Assertions.assertEquals("hW", namePair.getCamel());

        namePair = NameUtils.toNamePair("helloWord");
        System.out.println(namePair);
        Assertions.assertEquals("hello_word", namePair.getSnake());
        Assertions.assertEquals("helloWord", namePair.getCamel());

        namePair = NameUtils.toNamePair("HelloWord");
        System.out.println(namePair);
        Assertions.assertEquals("hello_word", namePair.getSnake());
        Assertions.assertEquals("HelloWord", namePair.getCamel());

    }

    @Test
    public void test2() {
        NamePair namePair = NameUtils.toNamePair("hello_word", CamelFormat.LOWER);
        System.out.println(namePair);
        Assertions.assertEquals("hello_word", namePair.getSnake());
        Assertions.assertEquals("helloWord", namePair.getCamel());

        namePair = NameUtils.toNamePair("HELLO_WORD", CamelFormat.LOWER);
        System.out.println(namePair);
        Assertions.assertEquals("HELLO_WORD", namePair.getSnake());
        Assertions.assertEquals("helloWord", namePair.getCamel());


        namePair = NameUtils.toNamePair("hello_word", CamelFormat.UPPER);
        System.out.println(namePair);
        Assertions.assertEquals("hello_word", namePair.getSnake());
        Assertions.assertEquals("HelloWord", namePair.getCamel());

        namePair = NameUtils.toNamePair("HELLO_WORD", CamelFormat.UPPER);
        System.out.println(namePair);
        Assertions.assertEquals("HELLO_WORD", namePair.getSnake());
        Assertions.assertEquals("HelloWord", namePair.getCamel());


        namePair = NameUtils.toNamePair("helloWord", SnakeFormat.LOWWER_UNDERSCORE);
        System.out.println(namePair);
        Assertions.assertEquals("helloWord", namePair.getCamel());
        Assertions.assertEquals("hello_word", namePair.getSnake());

        namePair = NameUtils.toNamePair("helloWord", SnakeFormat.LOWWER_HYPHEN);
        System.out.println(namePair);
        Assertions.assertEquals("helloWord", namePair.getCamel());
        Assertions.assertEquals("hello-word", namePair.getSnake());

        namePair = NameUtils.toNamePair("helloWord", SnakeFormat.UPPER_UNDERSCORE);
        System.out.println(namePair);
        Assertions.assertEquals("helloWord", namePair.getCamel());
        Assertions.assertEquals("HELLO_WORD", namePair.getSnake());

        namePair = NameUtils.toNamePair("helloWord", SnakeFormat.UPPER_HYPHEN);
        System.out.println(namePair);
        Assertions.assertEquals("helloWord", namePair.getCamel());
        Assertions.assertEquals("HELLO-WORD", namePair.getSnake());

    }
}