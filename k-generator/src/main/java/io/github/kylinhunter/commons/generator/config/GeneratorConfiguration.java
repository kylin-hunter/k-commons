package io.github.kylinhunter.commons.generator.config;

import io.github.kylinhunter.commons.generator.config.bean.Config;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-04 20:27
 **/
public class GeneratorConfiguration {
    private static Config config;

    private static void init() {
        ConfigParser configParser = new ConfigParser();
        Config   config = configParser.load();

    }
}
