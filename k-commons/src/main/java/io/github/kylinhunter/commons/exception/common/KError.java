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
public class KError extends Error implements KThrowable {

  private static final long serialVersionUID = 1L;
  private ErrInfo errInfo = ErrInfos.UNKNOWN;

  private Object extra;

  public KError(String message, Throwable cause) {
    super(message, cause);
  }

  public KError(String message) {
    super(message);
  }

  public KError(Throwable cause) {
    super(cause);
  }

  public KError(ErrInfo errInfo, String message, Throwable cause) {
    super(message, cause);
    this.errInfo = errInfo;
  }

  public KError(ErrInfo errInfo, String message) {
    super(message);
    this.errInfo = errInfo;
  }

  public KError(ErrInfo errInfo, Throwable cause) {
    super(cause);
    this.errInfo = errInfo;
  }
}
