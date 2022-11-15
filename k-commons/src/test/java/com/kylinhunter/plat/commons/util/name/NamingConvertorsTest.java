package com.kylinhunter.plat.commons.util.name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NamingConvertorsTest {

    @Test
    public void test() {
        String result = NamingConvertors.convert(NCStrategy.SNAKE_TO_CAMEL_UP_FIRST, "hello_word");
        System.out.println(result);
        Assertions.assertEquals("HelloWord", result);

        result = NamingConvertors.convert(NCStrategy.SNAKE_TO_CAMEL_UP_FIRST, "h_w");
        System.out.println(result);
        Assertions.assertEquals("HW", result);

        result = NamingConvertors.convert(NCStrategy.SNAKE_TO_CAMEL, "HELLO_WORD");
        System.out.println(result);
        Assertions.assertEquals("helloWord", result);

        result = NamingConvertors.convert(NCStrategy.SNAKE_TO_CAMEL, "H_W");
        System.out.println(result);
        Assertions.assertEquals("hW", result);

        result = NamingConvertors.convert(NCStrategy.CAMEL_TO_SNAKE, "helloWord");
        System.out.println(result);
        Assertions.assertEquals("hello_word", result);
        result = NamingConvertors.convert(NCStrategy.CAMEL_TO_SNAKE, "HelloWord");
        System.out.println(result);
        Assertions.assertEquals("hello_word", result);

    }

}