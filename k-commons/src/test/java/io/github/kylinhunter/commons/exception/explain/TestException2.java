package io.github.kylinhunter.commons.exception.explain;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-24 13:46
 */
public class TestException2 extends RuntimeException {

  @Getter
  private int value = 99;

  public TestException2(String message, Throwable cause) {
    super(message, cause);
  }
}
