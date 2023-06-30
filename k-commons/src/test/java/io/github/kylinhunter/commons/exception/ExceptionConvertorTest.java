package io.github.kylinhunter.commons.exception;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.explain.TestException1;
import io.github.kylinhunter.commons.exception.explain.TestException2;
import io.github.kylinhunter.commons.exception.explain.TestExplainerSupplier;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExceptionConvertorTest {

  @Test
  void convertDefault() {
    IllegalArgumentException illegalArgumentException = new IllegalArgumentException();
    KRuntimeException convertException = ExceptionConvertor.convert(illegalArgumentException);
    Assertions.assertEquals(KRuntimeException.class, convertException.getClass());
    Assertions.assertEquals(ErrInfos.PARAM, convertException.getErrInfo());
  }

  @Test
  void convertTestException1() {
    String extra = UUID.randomUUID().toString();
    String msg = UUID.randomUUID().toString();
    TestException1 testException1 = new TestException1(msg, new RuntimeException());
    testException1.setExtra(extra);

    KRuntimeException convert = ExceptionConvertor.convert(testException1, true);

    Assertions.assertEquals(TestException1.class, convert.getClass());
    Assertions.assertEquals(ErrInfos.BIZ, convert.getErrInfo());
    Assertions.assertEquals(extra, convert.getExtra());
    Assertions.assertEquals(msg, convert.getMessage());
    Assertions.assertNotNull(convert.getCause());

    convert = ExceptionConvertor.convert(testException1, false);
    Assertions.assertEquals(TestException1.class, convert.getClass());
    Assertions.assertEquals(ErrInfos.BIZ, convert.getErrInfo());
    Assertions.assertEquals(extra, convert.getExtra());
    Assertions.assertEquals(msg, convert.getMessage());
    Assertions.assertNotNull(convert.getCause());
  }

  @Test
  void convertTestException2() {
    String msg = UUID.randomUUID().toString();
    TestException2 testException2 = new TestException2(msg, new RuntimeException());

    KRuntimeException convert = ExceptionConvertor.convert(testException2, true);

    Assertions.assertEquals(KRuntimeException.class, convert.getClass());
    Assertions.assertEquals(TestExplainerSupplier.errInfoInfoTest, convert.getErrInfo());
    Assertions.assertEquals("errInfo2-msg", convert.getMessage());
    Assertions.assertEquals("extra", convert.getExtra());
    Assertions.assertNotNull(convert.getCause());

    convert = ExceptionConvertor.convert(testException2, false);
    Assertions.assertEquals(KRuntimeException.class, convert.getClass());
    Assertions.assertEquals(TestExplainerSupplier.errInfoInfoTest, convert.getErrInfo());
    Assertions.assertEquals("extra", convert.getExtra());
    Assertions.assertEquals("errInfo2-msg", convert.getMessage());
    Assertions.assertNull(convert.getCause());
  }
}
