package io.github.kylinhunter.commons.name;

import lombok.AllArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-06 14:52
 */
@AllArgsConstructor
public enum SnakeFormat {
  LOWWER_HYPHEN('-', true),
  UPPER_HYPHEN('-', false),
  LOWWER_UNDERSCORE('_', true),
  UPPER_UNDERSCORE('_', false);
  protected char seperator;
  protected boolean lower = true;
}
