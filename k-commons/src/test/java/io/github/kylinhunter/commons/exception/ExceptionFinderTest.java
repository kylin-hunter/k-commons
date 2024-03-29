package io.github.kylinhunter.commons.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.common.KException;
import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import org.junit.jupiter.api.Test;

class ExceptionFinderTest {

  @Test
  void findExcetion() {

    RuntimeException e1 = new RuntimeException();
    Exception e2 = new Exception(e1);
    KRuntimeException e3 = new KRuntimeException(e2);
    KException e4 = new KException(e3);

    Throwable e = ExceptionFinder.find(e4, false, RuntimeException.class);
    assertEquals(RuntimeException.class, e.getClass());

    e = ExceptionFinder.find(e4, true, RuntimeException.class);
    assertEquals(KRuntimeException.class, e.getClass());
  }

  @SuppressWarnings("unchecked")
  @Test
  void findExcetions() {

    RuntimeException e1 = new RuntimeException();
    Exception e2 = new Exception(e1);
    KRuntimeException e3 = new KRuntimeException(e2);
    KException e4 = new KException(e3);

    ExceptionFinder.ExceptionFind exceptionFind =
        ExceptionFinder.find(e4, false, SetUtils.newHashSet(RuntimeException.class));
    assertEquals(RuntimeException.class, exceptionFind.getTarget().getClass());
    assertEquals(RuntimeException.class, exceptionFind.getSource());

    exceptionFind = ExceptionFinder.find(e4, true, SetUtils.newHashSet(RuntimeException.class));
    assertEquals(KRuntimeException.class, exceptionFind.getTarget().getClass());
    assertEquals(RuntimeException.class, exceptionFind.getSource());
  }
}
