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
package io.github.kylinhunter.commons.utils.json;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-21 16:39
 */
public class JsonOptions {

  public static final JsonOption FAIL_NO_SNAKE = new JsonOption(true, false);
  public static final JsonOption FAIL_SNAKE = new JsonOption(true, true);
  public static final JsonOption NO_FAIL_NO_SNAKE = new JsonOption(false, false);
  public static final JsonOption NO_FAIL_SNAKE = new JsonOption(false, true);
  public static final JsonOption DEFAULT = FAIL_NO_SNAKE;
  public static final JsonOption NO_FAIL = NO_FAIL_NO_SNAKE;
}
