package io.github.kylinhunter.commons.exception.common;

import io.github.kylinhunter.commons.exception.info.ErrInfo;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
@Getter
@Setter
public class KException extends Exception implements KThrowable {
  private static final long serialVersionUID = 1L;
  private ErrInfo errInfo = ErrInfos.UNKNOWN;
  private Object extra;

  public KException() {}

  public KException(String message, Throwable cause) {
    super(message, cause);
  }

  public KException(String message) {
    super(message);
  }

  public KException(Throwable cause) {
    super(cause);
  }

  public KException(ErrInfo errInfo, String message, Throwable cause) {
    super(message, cause);
    this.errInfo = errInfo;
  }

  public KException(ErrInfo errInfo, String message) {
    super(message);
    this.errInfo = errInfo;
  }

  public KException(ErrInfo errInfo, Throwable cause) {
    super(cause);
    this.errInfo = errInfo;
  }
}
