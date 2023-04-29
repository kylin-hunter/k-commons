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
