package io.github.kylinhunter.commons.generator.config;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.generator.config.bean.Config;

class ConfigReaderTest {

    @Test
    void load() {
        ConfigReader configReader = new ConfigReader();
        Config config = configReader.load();
        System.out.println(config);
    }
}