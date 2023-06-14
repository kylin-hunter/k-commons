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
package io.github.kylinhunter.commons.name;

import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-06 14:52
 */
@Getter
public enum NameRule {
  SNAKE_LOW_HYPHEN(SnakeFormat.LOWWER_HYPHEN),
  SNAKE_LOW_UNDERSCORE(SnakeFormat.LOWWER_UNDERSCORE),
  SNAKE_UPPER_HYPHEN(SnakeFormat.UPPER_HYPHEN),
  SNAKE_UPPER_UNDERSCORE(SnakeFormat.UPPER_UNDERSCORE),
  CAMEL_LOW(CamelFormat.LOWER),
  CAMEL_UPPER(CamelFormat.UPPER);

  private CamelFormat camelFormat;
  private SnakeFormat snakeFormat;
  private boolean isCamel;

  NameRule(CamelFormat camelFormat) {
    this.camelFormat = camelFormat;
    this.isCamel = true;
  }

  NameRule(SnakeFormat snakeFormat) {
    this.snakeFormat = snakeFormat;
    this.isCamel = false;
  }
}
