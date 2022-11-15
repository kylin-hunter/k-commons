package com.kylinhunter.plat.commons.util.name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NamePairUtilsTest {
    @Test
    public void test() {
        NamePair namePair = NamePairUtils.toNamePair("hello_word");
        System.out.println(namePair);
        Assertions.assertEquals("hello_word", namePair.getSnake());
        Assertions.assertEquals("helloWord", namePair.getCamel());

        namePair = NamePairUtils.toNamePair("h_w");
        System.out.println(namePair);
        Assertions.assertEquals("h_w", namePair.getSnake());
        Assertions.assertEquals("hW", namePair.getCamel());

        namePair = NamePairUtils.toNamePair("HELLO_WORD");
        System.out.println(namePair);
        Assertions.assertEquals("HELLO_WORD", namePair.getSnake());
        Assertions.assertEquals("helloWord", namePair.getCamel());

        namePair = NamePairUtils.toNamePair("H_W");
        System.out.println(namePair);
        Assertions.assertEquals("H_W", namePair.getSnake());
        Assertions.assertEquals("hW", namePair.getCamel());

        namePair = NamePairUtils.toNamePair("helloWord");
        System.out.println(namePair);
        Assertions.assertEquals("hello_word", namePair.getSnake());
        Assertions.assertEquals("helloWord", namePair.getCamel());

        namePair = NamePairUtils.toNamePair("HelloWord");
        System.out.println(namePair);
        Assertions.assertEquals("hello_word", namePair.getSnake());
        Assertions.assertEquals("HelloWord", namePair.getCamel());

    }
}