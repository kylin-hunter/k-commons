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
public class InitException extends KRuntimeException {

  private static final long serialVersionUID = 1L;

  public InitException() {
    this.errInfo = ErrInfos.INIT;
  }

  public InitException(String message, Throwable cause) {
    super(ErrInfos.INIT, message, cause);
  }

  public InitException(String message) {
    super(ErrInfos.INIT, message);
  }

  public InitException(Throwable cause) {
    super(ErrInfos.INIT, cause);
  }
}
