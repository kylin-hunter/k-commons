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
package io.github.kylinhunter.commons.exception.info;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 */
@ErrInfoAware
public class ErrInfos {

  public static final int CODE_SUCCESS = 0;
  public static final String MSG_SUCCESS = "ok";

  public static final ErrInfo UNKNOWN = new ErrInfo(-1, "UNKNOWN");

  private static int BASE_CODE = 1000;
  public static final ErrInfo FORMAT = new ErrInfo(++BASE_CODE);
  public static final ErrInfo INIT = new ErrInfo(++BASE_CODE);
  public static final ErrInfo INTERNAL = new ErrInfo(++BASE_CODE);
  public static final ErrInfo IO = new ErrInfo(++BASE_CODE);
  public static final ErrInfo PARAM = new ErrInfo(++BASE_CODE);
  public static final ErrInfo GENERAL = new ErrInfo(++BASE_CODE);
  public static final ErrInfo SYSTEM = new ErrInfo(++BASE_CODE);

  public static final ErrInfo CRYPT = new ErrInfo(++BASE_CODE);


  private static int BIZ_CODE = 1100;
  public static final ErrInfo BIZ = new ErrInfo(++BIZ_CODE);


}
