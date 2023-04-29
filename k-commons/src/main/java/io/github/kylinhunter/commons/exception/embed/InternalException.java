package io.github.kylinhunter.commons.exception.embed;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class InternalException extends KRuntimeException {
  private static final long serialVersionUID = 1L;

  public InternalException() {
    this.errInfo = ErrInfos.INTERNAL;
  }

  public InternalException(String message, Throwable cause) {
    super(ErrInfos.INTERNAL, message, cause);
  }

  public InternalException(String message) {
    super(ErrInfos.INTERNAL, message);
  }

  public InternalException(Throwable cause) {
    super(ErrInfos.INTERNAL, cause);
  }
}
