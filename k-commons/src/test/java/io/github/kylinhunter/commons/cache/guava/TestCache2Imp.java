package io.github.kylinhunter.commons.cache.guava;

import java.util.concurrent.TimeUnit;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 13:36
 */
class TestCache2Imp extends AbstractCache<Long> implements TestCache2 {

  long l = 0;

  @SuppressWarnings("NullableProblems")
  @Override
  public Long load(CacheKey cacheKey) {
    Object[] params = cacheKey.getParams();
    if (params.length == 2) {
      return l++;
    }
    return 0L;
  }

  @Override
  public Long get(TestParam1 param1, TestParam2 param2) {
    return super.get(param1, param2);
  }

  @Override
  public void invalidate(TestParam1 param1, TestParam2 param2) {
    super.invalidate(param1, param2);
  }

  @Override
  public void put(TestParam1 param1, TestParam2 param2, Long value) {
    super.put(new Object[] {param1, param2}, value);
  }

  @Override
  protected void custom(CacheConfig cacheConfig) {
    cacheConfig.setRefreshAfterWrite(2); //
    cacheConfig.setRefreshTimeUnit(TimeUnit.MILLISECONDS);
  }
}
