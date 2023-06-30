package io.github.kylinhunter.commons.exception.explain;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-24 13:46
 */
public class TestException1 extends BizException {

  public TestException1(String message, Throwable cause) {
    super(message, cause);
  }
}
