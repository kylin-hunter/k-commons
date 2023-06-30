package io.github.kylinhunter.commons.exception.wrapper;

import io.github.kylinhunter.commons.exception.embed.SystemException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-19 23:41
 */
public class ExceptionWrapperBean {


  @ExceptionWrapper
  public static void a() {
    throw new RuntimeException("a");

  }

  @ExceptionWrapper(value = SystemException.class)
  public void b() {
    throw new RuntimeException("b");

  }

  @ExceptionWrapper(code = 999)
  public void c() {
    throw new RuntimeException("c");

  }

  @ExceptionWrapper(value = SystemException.class, code = 999, msg = "d")
  public void d() {
    throw new RuntimeException("d");

  }

}