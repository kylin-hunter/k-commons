package io.github.kylinhunter.commons.cache;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.util.TheadHelper;

public class GuavaKCacheTest {

    @Test
    void test() {
        KCache<String, Long> guavaCache = new TestCacheImp();
        String key = "a";
        Long value = guavaCache.get(key);

        while (value < 3) {
            System.out.println(value);
            value = guavaCache.get(key);
            TheadHelper.sleep(100, TimeUnit.MILLISECONDS);
        }
        guavaCache.put(key, 100L);
        value = guavaCache.get(key);
        System.out.println(value);
        guavaCache.invalidate(key);
        value = guavaCache.get(key);
        System.out.println(value);

    }

}

class TestCacheImp extends GuavaCache<String, Long> {

    long l = 0;

    @Override
    public Long load(String key) {
        return l++;
    }

    @Override
    public GuavaCacheConfig loadConfig() {
        GuavaCacheConfig guavaCacheConfig = new GuavaCacheConfig();
        guavaCacheConfig.setRefreshAfterWrite(2); //
        guavaCacheConfig.setRefreshTimeUnit(TimeUnit.MILLISECONDS);
        return guavaCacheConfig;
    }
}