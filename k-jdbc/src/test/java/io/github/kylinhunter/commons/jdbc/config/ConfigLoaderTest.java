package io.github.kylinhunter.commons.jdbc.config;

import io.github.kylinhunter.commons.jdbc.config.datasource.hikari.ExHikariConfig;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ConfigLoaderTest {

  @Test
  void load() {

    ConfigLoader configLoader = new ConfigLoader();

    List<ExHikariConfig> exHikariConfigs = configLoader.load();
    Assertions.assertEquals(2, exHikariConfigs.size());
    exHikariConfigs.get(0).getJdbcUrl();

  }
}