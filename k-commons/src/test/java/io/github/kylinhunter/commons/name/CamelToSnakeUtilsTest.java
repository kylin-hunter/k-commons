package io.github.kylinhunter.commons.name;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CamelToSnakeUtilsTest {

  @Test
  void convert() {

    String result = CamelToSnakeUtils.convert("helloWord");
    System.out.println(result);
    Assertions.assertEquals("hello_word", result);

    result = CamelToSnakeUtils.convert("helloWord", SnakeFormat.LOWWER_UNDERSCORE);
    System.out.println(result);
    Assertions.assertEquals("hello_word", result);

    result = CamelToSnakeUtils.convert("HelloWord", SnakeFormat.LOWWER_HYPHEN);
    System.out.println(result);
    Assertions.assertEquals("hello-word", result);

    result = CamelToSnakeUtils.convert("HelloWord", SnakeFormat.UPPER_UNDERSCORE);
    System.out.println(result);
    Assertions.assertEquals("HELLO_WORD", result);

    result = CamelToSnakeUtils.convert("HelloWord", SnakeFormat.UPPER_HYPHEN);
    System.out.println(result);
    Assertions.assertEquals("HELLO-WORD", result);

    result = CamelToSnakeUtils.convert("HELLOWORD", SnakeFormat.LOWWER_UNDERSCORE);
    System.out.println(result);
    Assertions.assertEquals("h_e_l_l_o_w_o_r_d", result);

    result = CamelToSnakeUtils.convert("HELLO-WORD", SnakeFormat.UPPER_HYPHEN);
    System.out.println(result);
    Assertions.assertEquals("HELLO-WORD", result);
  }
}
