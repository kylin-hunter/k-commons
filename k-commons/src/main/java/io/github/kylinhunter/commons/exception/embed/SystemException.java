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
package io.github.kylinhunter.commons.exception.embed;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
public class SystemException extends KRuntimeException {
  private static final long serialVersionUID = 1L;

  public SystemException() {
    this.errInfo = ErrInfos.SYSTEM;
  }

  public SystemException(String message) {
    super(ErrInfos.SYSTEM, message);
  }

  public SystemException(String message, Throwable cause) {
    super(ErrInfos.SYSTEM, message, cause);
  }
}
