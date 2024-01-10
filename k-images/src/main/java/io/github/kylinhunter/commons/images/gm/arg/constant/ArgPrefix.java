package io.github.kylinhunter.commons.images.gm.arg.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-11 01:39
 */
@Getter
@RequiredArgsConstructor
public enum ArgPrefix {
  PLUS("+"),
  MINUS("-");

  private final String prefix;

}