package io.github.kylinhunter.commons.generator.config;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.generator.config.bean.Config;

class ConfigParserTest {

    @Test
    void load() {
        ConfigParser configParser = new ConfigParser();
        Config config = configParser.load();
        System.out.println(config);
    }
}