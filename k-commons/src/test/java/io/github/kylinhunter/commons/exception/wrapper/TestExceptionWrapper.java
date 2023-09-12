package io.github.kylinhunter.commons.exception.wrapper;

import io.github.kylinhunter.commons.exception.embed.SystemException;
import io.github.kylinhunter.commons.exception.embed.biz.BizException;
import io.github.kylinhunter.commons.init.AbstractInitializer;
import io.github.kylinhunter.commons.init.KCommons;
import org.junit.jupiter.api.Assertions;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-19 11:00
 */
public class TestExceptionWrapper {


  public static void main(String[] args) {

    KCommons.custom().debug(true).init();
    ExceptionWarapperInitializer exceptionWarapperInitializer = AbstractInitializer.get(
        ExceptionWarapperInitializer.class);
    exceptionWarapperInitializer.initialize(
        "io.github.kylinhunter.commons.exception.wrapper.ExceptionWrapperBean");
    Assertions.assertThrows(BizException.class, ExceptionWrapperBean::a);

    Assertions.assertThrows(SystemException.class, () -> {
      new ExceptionWrapperBean().b();
    });

    Assertions.assertThrows(BizException.class, () -> {
      try {
        new ExceptionWrapperBean().c();
      } catch (BizException e) {
        Assertions.assertEquals(999, e.getErrInfo().getCode());
        throw e;
      }
    });

    Assertions.assertThrows(SystemException.class, () -> {
      try {
        new ExceptionWrapperBean().d();
      } catch (SystemException e) {
        Assertions.assertEquals(999, e.getErrInfo().getCode());
        Assertions.assertEquals("d", e.getErrInfo().getDefaultMsg());

        throw e;
      }
    });

  }

}