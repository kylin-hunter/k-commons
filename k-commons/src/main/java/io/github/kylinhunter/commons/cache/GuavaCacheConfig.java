package io.github.kylinhunter.commons.cache;

import java.util.concurrent.TimeUnit;

import lombok.Data;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-08-16 19:57
 **/
@Data
public class GuavaCacheConfig {

    @Getter
    private static GuavaCacheConfig defaultConfig = new GuavaCacheConfig();

    static {

        defaultConfig.setRefreshAfterWrite(1);
    }

    /**
     * maxSize
     */
    private int maxSize = 10000;

    /**
     * expireAfterWrite（optional）
     */
    private int expireAfterWrite = -1;

    /**
     * expireTimeUnit
     */
    private TimeUnit expireTimeUnit = TimeUnit.MINUTES;

    /**
     * refreshAfterWrite
     */
    private int refreshAfterWrite = -1;

    /**
     * refreshTimeUnit
     */
    private TimeUnit refreshTimeUnit = TimeUnit.MINUTES;

}
