package io.github.kylinhunter.commons.generator.config;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.ModuleInfo;
import io.github.kylinhunter.commons.generator.config.bean.ModuleInfos;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfigs;
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
public class ConfigReader {
    private static final String DEFAULT_PATH = "k-generator-config.yaml";

    public Config load() {
        return this.load(null);
    }

    /**
     * @param path path
     * @return io.github.kylinhunter.commons.generator.config.bean.Config
     * @title load
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:23
     */
    public Config load(String path) {
        path = StringUtils.defaultString(path, DEFAULT_PATH);
        Config config = YamlHelper.loadFromPath(Config.class, path);
        return afterLoad(config);
    }

    /**
     * @param config config
     * @return void
     * @title afterLoad
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:23
     */
    private Config afterLoad(Config config) {

        ModuleInfos modules = config.getModules();
        for (ModuleInfo moduleInfo : modules.getList()) {
            moduleInfo.merge(modules);
        }

        TemplateConfigs templates = config.getTemplates();
        for (TemplateConfig template : templates.getList()) {
            template.merge(templates);
        }
        return config;
    }

}
