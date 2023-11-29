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
package io.github.kylinhunter.commons.jdbc.exception;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;

/**
 * @author BiJi'an
 * @description JdbcException
 * @date 2023-01-17 23:47
 */
public class JdbcException extends BizException {

  public JdbcException(Throwable cause) {
    super(JdbcErrInfos.JDBC, "JDBC", cause);
  }

  public JdbcException(String message, Throwable cause) {
    super(JdbcErrInfos.JDBC, message, cause);
  }

  public JdbcException(String message) {
    super(JdbcErrInfos.JDBC, message);
  }
}
