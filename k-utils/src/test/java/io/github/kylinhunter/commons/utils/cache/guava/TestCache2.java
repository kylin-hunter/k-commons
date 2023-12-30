package io.github.kylinhunter.commons.utils.cache.guava;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 13:38
 */
public interface TestCache2 {

  Long get(TestParam1 param1, TestParam2 param2);

  void invalidate(TestParam1 param1, TestParam2 param2);

  void put(TestParam1 param1, TestParam2 param2, Long value);
}
