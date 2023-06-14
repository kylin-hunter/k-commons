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
import java.util.function.Function;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-24 02:45
 */
@Data
@Accessors(chain = true)
public class Explainer<T extends Throwable> {
  private final Class<T> source;
  private Function<T, ExplainResult> explainer;

  /**
   * @author BiJi'an
   * @description
   * @date 2022-01-19 18:59
   */
  @Data
  public static class ExplainResult {
    private ErrInfo errInfo;
    private Object extra;
    private String msg;

    public ExplainResult(KThrowable KThrowable, String msg) {
      this.errInfo = KThrowable.getErrInfo();
      if (this.errInfo == null) {
        this.errInfo = ErrInfos.UNKNOWN;
      }
      this.extra = KThrowable.getExtra();
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

    public String getMsg() {
      if (this.msg != null && this.msg.length() > 0) {
        return this.msg;
      } else {
        return this.errInfo.getDefaultMsg();
      }
    }
  }
}
