package io.github.kylinhunter.commons.collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ArrayUtilsTest {

  @Test
  void isEmpty() {

    Object[] arr = new Object[]{1, 2};

    assertFalse(ArrayUtils.isEmpty(arr));
    assertTrue(ArrayUtils.isEmpty((Object[]) null));
    String[] arr2 = new String[]{"1", "2"};
    assertFalse(ArrayUtils.isEmpty(arr2));
  }
}
