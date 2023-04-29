package io.github.kylinhunter.commons.generator.exception;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:31
 */
public class CodeException extends BizException {
  public CodeException(String message, Throwable cause) {
    super(message, cause);
  }

  public CodeException(String message) {
    super(message);
  }

  public CodeException(Throwable cause) {
    super(cause);
  }
}
