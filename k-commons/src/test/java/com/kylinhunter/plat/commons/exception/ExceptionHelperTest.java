package com.kylinhunter.plat.commons.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.kylinhunter.plat.commons.exception.explain.ExceptionConverterTest;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

class ExceptionHelperTest {
    @Test
    void test() {

        ExceptionConverterTest.TestException1 testException1 =
                new ExceptionConverterTest.TestException1("TestException1's msg", new RuntimeException());

        ExceptionConverterTest.TestException2 testException2 =
                new ExceptionConverterTest.TestException2("the-unkown-msg", new RuntimeException());

        Assertions.assertEquals(ErrInfos.BIZ.getCode(), ExceptionHelper.getErrCode(testException1));

        Assertions.assertEquals(-1, ExceptionHelper.getErrCode(testException2));

        Assertions.assertEquals("TestException1's msg", ExceptionHelper.getMessage(testException1));

        Assertions.assertEquals("Te", ExceptionHelper.getMessage(testException1, true, 2));
        Assertions.assertEquals("Te", ExceptionHelper.getMessage(testException1, false, 2));

        Assertions.assertEquals("the-unkown-msg", ExceptionHelper.getMessage(testException2, true, 100));
        Assertions.assertEquals("the", ExceptionHelper.getMessage(testException2, true, 3));
        Assertions.assertEquals("UNKNOWN", ExceptionHelper.getMessage(testException2, false, 100));
        Assertions.assertEquals("UNK", ExceptionHelper.getMessage(testException2, false, 3));

    }

}