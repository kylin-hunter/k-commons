package io.github.kylinhunter.commons.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HexUtilsTest {
  @Test
  void bytesToHex() {

    byte[] bytes = new byte[] {127, 126, 15, 55, 32};

    String hex = HexUtils.toString(bytes);
    System.out.println(hex);

    Assertions.assertTrue(HexUtils.isHex(hex));
    byte[] bytesNew = HexUtils.toBytes(hex);
    Assertions.assertArrayEquals(bytes, bytesNew);
  }
}
