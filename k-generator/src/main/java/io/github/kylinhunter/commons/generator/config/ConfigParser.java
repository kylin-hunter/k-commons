package io.github.kylinhunter.commons.generator.config;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.yaml.YamlHelper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 16:12
 **/
@Getter
@Slf4j
public class ConfigParser {
    private static final String DEFAULT_PATH = "k-generator-config.yaml";

    public Config load() {
        return this.load(null);
    }

    /**
     * @param path path
     * @return com.zaxxer.hikari.HikariConfig
     * @title buildConfig
     * @description
     * @author BiJi'an
     * @date 2023-01-17 22:27
     */
    public Config load(String path) {
        path = StringUtils.defaultString(path, DEFAULT_PATH);
        Config config = YamlHelper.loadFromClassPath(Config.class, path);

        return config;
    }

}
