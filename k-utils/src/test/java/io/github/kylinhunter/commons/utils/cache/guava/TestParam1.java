package io.github.kylinhunter.commons.utils.cache.guava;

import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 13:39
 */
@RequiredArgsConstructor
public class TestParam1 {

  @Cache.Include
  private final String param1;
}
