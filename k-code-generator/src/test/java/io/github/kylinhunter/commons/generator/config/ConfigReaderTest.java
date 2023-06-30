package io.github.kylinhunter.commons.generator.config;

import io.github.kylinhunter.commons.generator.config.bean.Config;
import org.junit.jupiter.api.Test;

class ConfigReaderTest {

  @Test
  void load() {
    ConfigReader configReader = new ConfigReader();
    Config config = configReader.load();
    System.out.println(config);
  }
}
