package io.github.kylinhunter.commons.jdbc.config;

import io.github.kylinhunter.commons.jdbc.config.datasource.hikari.ExHikariConfig;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DSConfigLoaderTest {

  @Test
  void load() {

    DSConfigLoader DSConfigLoader = new DSConfigLoader();

    List<ExHikariConfig> exHikariConfigs = DSConfigLoader.load();
    Assertions.assertEquals(2, exHikariConfigs.size());
    String jdbcUrl = exHikariConfigs.get(0).getJdbcUrl();
    Assertions.assertNotNull(jdbcUrl);

  }
}