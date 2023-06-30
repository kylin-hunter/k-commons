package io.github.kylinhunter.commons.exception;

import io.github.kylinhunter.commons.exception.explain.TestException1;
import io.github.kylinhunter.commons.exception.explain.TestException2;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ExceptionHelperTest {
  @Test
  void test() {

    TestException1 testException1 =
        new TestException1("TestException1's msg", new RuntimeException());

    TestException2 testException2 = new TestException2("the-unkown-msg", new RuntimeException());

    Assertions.assertEquals(ErrInfos.BIZ.getCode(), ExceptionHelper.getErrCode(testException1));

    Assertions.assertEquals(-1, ExceptionHelper.getErrCode(testException2));

    Assertions.assertEquals("TestException1's msg", ExceptionHelper.getMessage(testException1));

    Assertions.assertEquals("Te", ExceptionHelper.getMessage(testException1, true, 2));
    Assertions.assertEquals("Te", ExceptionHelper.getMessage(testException1, false, 2));

    Assertions.assertEquals(
        "the-unkown-msg", ExceptionHelper.getMessage(testException2, true, 100));
    Assertions.assertEquals("the", ExceptionHelper.getMessage(testException2, true, 3));
    Assertions.assertEquals("UNKNOWN", ExceptionHelper.getMessage(testException2, false, 100));
    Assertions.assertEquals("UNK", ExceptionHelper.getMessage(testException2, false, 3));
  }
}
