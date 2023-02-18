package io.github.kylinhunter.commons.generator.configuration;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CC;
import io.github.kylinhunter.commons.generator.config.ConfigReader;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.ResourceHelper.PathType;
import io.github.kylinhunter.commons.template.TemplateEngine;
import io.github.kylinhunter.commons.template.velocity.VelocityTemplateEngine;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-04 20:27
 **/
@CC
public class CompConfiguration {

    /**
     * @return io.github.kylinhunter.commons.generator.config.bean.Config
     * @title config
     * @description
     * @author BiJi'an
     * @date 2023-02-16 23:51
     */
    @C
    public Config config() {
        return new ConfigReader().load();
    }

    /**
     * @param config
     * @return io.github.kylinhunter.commons.template.TemplateExecutor
     * @title templateExecutor
     * @description
     * @author BiJi'an
     * @date 2023-02-16 23:51
     */
    @C
    public TemplateEngine templateEngine(Config config) {
        TemplateEngine templateEngine = new VelocityTemplateEngine();
        templateEngine.customize(templateConfig -> {
            Global global = config.getGlobal();

            String templatePath = global.getTemplatePath();
            templateConfig.setTemplateDir(ResourceHelper.getDir(templatePath, PathType.FILESYSTEM).toPath());

            String outputPathPath = global.getOutputPath();
            templateConfig.setOutputDir(ResourceHelper.getDir(outputPathPath, PathType.FILESYSTEM).toPath());

        });
        return templateEngine;

    }

}
