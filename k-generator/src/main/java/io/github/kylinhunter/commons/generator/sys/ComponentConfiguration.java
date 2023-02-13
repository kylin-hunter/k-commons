package io.github.kylinhunter.commons.generator.sys;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CC;
import io.github.kylinhunter.commons.generator.CodeTemplateEngine;
import io.github.kylinhunter.commons.generator.config.ConfigReader;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.template.TemplateExecutor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-04 20:27
 **/
@CC
public class ComponentConfiguration {

    @C
    public Config config() {
        ConfigReader configReader = new ConfigReader();
        return configReader.load();

    }

    @C
    public TemplateExecutor templateExecutor() {
        CodeTemplateEngine codeTemplateEngine = new CodeTemplateEngine();
        return codeTemplateEngine.createTemplateExecutor();

    }
}
