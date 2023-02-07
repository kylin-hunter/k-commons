package io.github.kylinhunter.commons.name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.component.CF;

class CamelToSnakeTest {

    @Test
    void convert() {
        CamelToSnake camelToSnake = CF.get(CamelToSnake.class);

        String result = camelToSnake.convert("helloWord");
        System.out.println(result);
        Assertions.assertEquals("hello_word", result);

        result = camelToSnake.convert("helloWord", SnakeFormat.LOWWER_UNDERSCORE);
        System.out.println(result);
        Assertions.assertEquals("hello_word", result);

        result = camelToSnake.convert("HelloWord", SnakeFormat.LOWWER_HYPHEN);
        System.out.println(result);
        Assertions.assertEquals("hello-word", result);

        result = camelToSnake.convert("HelloWord", SnakeFormat.UPPER_UNDERSCORE);
        System.out.println(result);
        Assertions.assertEquals("HELLO_WORD", result);

        result = camelToSnake.convert("HelloWord", SnakeFormat.UPPER_HYPHEN);
        System.out.println(result);
        Assertions.assertEquals("HELLO-WORD", result);


        result = camelToSnake.convert("HELLOWORD", SnakeFormat.LOWWER_UNDERSCORE);
        System.out.println(result);
        Assertions.assertEquals("h_e_l_l_o_w_o_r_d", result);

        result = camelToSnake.convert("HELLO-WORD", SnakeFormat.UPPER_HYPHEN);
        System.out.println(result);
        Assertions.assertEquals("HELLO-WORD", result);

    }
}