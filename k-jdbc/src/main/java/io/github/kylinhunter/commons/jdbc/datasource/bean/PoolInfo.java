package io.github.kylinhunter.commons.jdbc.datasource.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 20:09
 **/
@Data
public class PoolInfo {

    private int poolName; //  Default: auto-generated

    private int minimumIdle; // Default: same as maximumPoolSize
    private int maximumPoolSize; // Default: 10
    private int connectionTimeout; //default 30000

    private int idleTimeout; // Default: 600000 (10 minutes)
    private int maxLifetime; // Default: 1800000 (30 minutes)
    private int validationTimeout; // Default: 5000

    private String connectionTestQuery; // SELECT 1 FROM DUAL

}

