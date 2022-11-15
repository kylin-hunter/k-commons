package com.kylinhunter.plat.commons.exception.explain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.kylinhunter.plat.commons.exception.common.KRuntimeException;
import com.kylinhunter.plat.commons.exception.info.ErrInfo;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;
import com.kylinhunter.plat.commons.exception.inner.biz.BizException;

public class ExceptionConverterTest {
    public static ExceptionExplainer exceptionExplainer = new ExceptionExplainer();
    public static ErrInfo errInfoInfoTest = new ErrInfo(-99, -99, "default msg");

    @BeforeAll
    static void init() {
        exceptionExplainer.register(TestException2.class, e -> {
            ExplainInfo explainInfo = new ExplainInfo(errInfoInfoTest, "TestException2's msg");
            explainInfo.setExtra("TestException2's extra");
            return explainInfo;
        });

    }

    public static class TestException1 extends BizException {
        public TestException1(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public static class TestException2 extends RuntimeException {
        public TestException2(String message, Throwable cause) {
            super(message, cause);
        }
    }

    @Test
    void convert() {

        TestException1 testException1 = new TestException1("TestException1's msg", new RuntimeException());
        testException1.setExtra("TestException1's extra");

        TestException2 testException2 = new TestException2("test2", new RuntimeException());

        KRuntimeException convert = exceptionExplainer.convert(testException1, true);

        Assertions.assertEquals(TestException1.class, convert.getClass());
        Assertions.assertEquals(ErrInfos.BIZ, convert.getErrInfo());
        Assertions.assertEquals("TestException1's extra", convert.getExtra());
        Assertions.assertEquals("TestException1's msg", convert.getMessage());

        Assertions.assertNotNull(convert.getCause());
        convert = exceptionExplainer.convert(testException1, false);

        Assertions.assertEquals(TestException1.class, convert.getClass());
        Assertions.assertEquals(ErrInfos.BIZ, convert.getErrInfo());
        Assertions.assertEquals("TestException1's extra", convert.getExtra());
        Assertions.assertEquals("TestException1's msg", convert.getMessage());

        Assertions.assertNotNull(convert.getCause());

        convert = exceptionExplainer.convert(testException2, true);

        Assertions.assertEquals(KRuntimeException.class, convert.getClass());
        Assertions.assertEquals(errInfoInfoTest, convert.getErrInfo());
        Assertions.assertEquals("TestException2's extra", convert.getExtra());
        Assertions.assertEquals("TestException2's msg", convert.getMessage());
        Assertions.assertNotNull(convert.getCause());

        convert = exceptionExplainer.convert(testException2, false);

        Assertions.assertEquals(KRuntimeException.class, convert.getClass());
        Assertions.assertEquals(errInfoInfoTest, convert.getErrInfo());
        Assertions.assertEquals("TestException2's extra", convert.getExtra());
        Assertions.assertEquals("TestException2's msg", convert.getMessage());
        Assertions.assertNull(convert.getCause());

    }

}