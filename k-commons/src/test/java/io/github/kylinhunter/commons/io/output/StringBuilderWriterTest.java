package io.github.kylinhunter.commons.io.output;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringBuilderWriterTest {

  @Test
  void test() {
    StringBuilderWriter sw = new StringBuilderWriter();
    sw.append("test1");
    sw.write("test2");
    sw.write(new char[]{'t', 'e', 's', 't', '3'}, 0, 5);
    sw.append('c');
    String result = sw.toString();
    System.out.println(result);
    Assertions.assertEquals(16, result.length());
  }
}