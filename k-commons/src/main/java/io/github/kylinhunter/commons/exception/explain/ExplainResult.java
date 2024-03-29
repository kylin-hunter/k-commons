/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.exception.explain;

import io.github.kylinhunter.commons.exception.common.KThrowable;
import io.github.kylinhunter.commons.exception.info.ErrInfo;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.sys.KGenerated;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-21 23:24
 */
@Getter
@Setter
@KGenerated
public class ExplainResult {

  private ErrInfo errInfo;
  private Object extra;
  private String msg;

  public ExplainResult(KThrowable throwable) {
    this.errInfo = throwable.getErrInfo();
    if (this.errInfo == null) {
      this.errInfo = ErrInfos.UNKNOWN;
    }
    this.extra = throwable.getExtra();
    this.msg = throwable.getMessage();
  }

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
