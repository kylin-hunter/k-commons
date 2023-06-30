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
package io.github.kylinhunter.commons.exception.embed.biz;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.info.ErrInfo;
import io.github.kylinhunter.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class BizException extends KRuntimeException {

  private static final long serialVersionUID = 1L;

  public BizException() {
    this.errInfo = ErrInfos.BIZ;
  }

  public BizException(String message, Throwable cause) {
    this(ErrInfos.BIZ, message, cause);
  }

  public BizException(String message) {
    this(ErrInfos.BIZ, message);
  }

  public BizException(Throwable cause) {
    this(ErrInfos.BIZ, cause);
  }

  public BizException(ErrInfo errInfo, String message, Throwable cause) {
    super(errInfo, message, cause);
  }

  public BizException(ErrInfo errInfo, String message) {
    super(errInfo, message);
  }

  public BizException(ErrInfo errInfo, Object extra) {
    super(errInfo, "");
    this.setExtra(extra);
  }

  public BizException(ErrInfo errInfo, Throwable cause) {
    super(errInfo, cause);
  }
}
