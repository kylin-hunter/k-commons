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

import io.github.kylinhunter.commons.exception.info.ErrInfo;
import io.github.kylinhunter.commons.exception.info.ErrInfoAware;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 00:53
 */
@ErrInfoAware
public class JdbcErrInfos {

  static int BASE_CODE = 3000;

  public static final ErrInfo JDBC = new ErrInfo(++BASE_CODE);
  public static final ErrInfo FAST_FAIL = new ErrInfo(++BASE_CODE);

}
