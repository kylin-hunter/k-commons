package io.github.kylinhunter.commons.exception.check;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionCheckerTest {

  @Test
  void test() {

    Assertions.assertThrows(
        KRuntimeException.class, () -> ExceptionChecker.checkNotEmpty("", "test"));
    ExceptionChecker.checkNotEmpty("11", "test");

    Assertions.assertThrows(
        KRuntimeException.class, () -> ExceptionChecker.checkNotNull(null, "test"));
    ExceptionChecker.checkNotNull("11", "test");

    Assertions.assertThrows(
        KRuntimeException.class, () -> ExceptionChecker.checkNonnegative(-1, "test"));
    ExceptionChecker.checkNonnegative(1, "test");

    Assertions.assertThrows(
        KRuntimeException.class, () -> ExceptionChecker.checkArgument(false, "test"));
    ExceptionChecker.checkArgument(true, "test");
  }

  @Test
  void checkNum() {
    Assertions.assertThrows(
        KRuntimeException.class, () -> ExceptionChecker.checkNum(-1, 0, 10));

    ExceptionChecker.checkNum(0, 0, 10);
    ExceptionChecker.checkNum(9, 0, 10);

    Assertions.assertThrows(
        KRuntimeException.class, () -> ExceptionChecker.checkNum(10, 0, 10));


  }
}
