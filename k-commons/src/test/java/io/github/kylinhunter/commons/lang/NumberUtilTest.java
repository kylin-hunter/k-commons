package io.github.kylinhunter.commons.lang;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberUtilTest {

  @Test
  void test() {
    Assertions.assertEquals((short) 1, NumberUtil.toShort("1"));

    Assertions.assertEquals(2, NumberUtil.toDouble("2"));

    Assertions.assertEquals((float) 3, NumberUtil.toFloat("3"));

    Assertions.assertEquals(4, NumberUtil.toInt("4"));
  }
}
