package io.github.kylinhunter.commons.exception.check;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ThrowCheckerTest {

  @Test
  void test() {

    Assertions.assertThrows(
        KRuntimeException.class, () -> ThrowChecker.checkNotEmpty("", "test"));
    ThrowChecker.checkNotEmpty("11", "test");

    Assertions.assertThrows(
        KRuntimeException.class, () -> ThrowChecker.checkNotNull(null, "test"));
    ThrowChecker.checkNotNull("11", "test");

    Assertions.assertThrows(
        KRuntimeException.class, () -> ThrowChecker.checkNonnegative(-1, "test"));
    ThrowChecker.checkNonnegative(1, "test");

    Assertions.assertThrows(
        KRuntimeException.class, () -> ThrowChecker.checkArgument(false, "test"));
    ThrowChecker.checkArgument(true, "test");
  }

  @Test
  void checkNum() {
    Assertions.assertThrows(
        KRuntimeException.class, () -> ThrowChecker.checkNum(-1, 0, 10));

    ThrowChecker.checkNum(0, 0, 10);
    ThrowChecker.checkNum(9, 0, 10);

    Assertions.assertThrows(
        KRuntimeException.class, () -> ThrowChecker.checkNum(10, 0, 10));


  }
}
