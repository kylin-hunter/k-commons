package io.github.kylinhunter.commons.cache.guava;

import java.util.concurrent.TimeUnit;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 13:36
 **/
class TestCache1Imp extends AbstractCache<Long> {

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
    public CacheConfig loadConfig() {
        CacheConfig cacheConfig = new CacheConfig();
        cacheConfig.setRefreshAfterWrite(2); //
        cacheConfig.setRefreshTimeUnit(TimeUnit.MILLISECONDS);
        return cacheConfig;
    }
}
