package io.github.kylinhunter.commons.exception.explain;

import io.github.kylinhunter.commons.exception.common.KThrowable;
import io.github.kylinhunter.commons.exception.info.ErrInfo;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-21 23:24
 */
@Getter
@Setter
public class ExplainResult {

  private ErrInfo errInfo;
  private Object extra;
  private String msg;

  public ExplainResult(KThrowable throwable, String msg) {
    this.errInfo = throwable.getErrInfo();
    if (this.errInfo == null) {
      this.errInfo = ErrInfos.UNKNOWN;
    }
    this.extra = throwable.getExtra();
    this.msg = msg;
  }

  public ExplainResult(ErrInfo errInfo, String msg) {
    this.errInfo = errInfo;
    if (this.errInfo == null) {
      this.errInfo = ErrInfos.UNKNOWN;
    }
    this.msg = StringUtil.defaultString(msg);
  }

  public ExplainResult(ErrInfo errInfo) {
    this(errInfo, null);
  }

  /**
   * @return java.lang.String
   * @title getMsg
   * @description getMsg
   * @author BiJi'an
   * @date 2023-06-21 23:40
   */
  public String getMsg() {
    if (this.msg != null && this.msg.length() > 0) {
      return this.msg;
    } else {
      return this.errInfo.getDefaultMsg();
    }
  }
}
