package io.github.kylinhunter.commons.generator.configuration;

import java.io.File;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CC;
import io.github.kylinhunter.commons.generator.config.ConfigReader;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.generator.exception.CodeException;
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
     * @param config config
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
            File dirTemplate = ResourceHelper.getDir(templatePath, PathType.FILESYSTEM);
            templateConfig.setTemplateDir(dirTemplate.toPath());
            if (dirTemplate == null || !dirTemplate.exists()) {
                throw new CodeException("invalid templatePath" + templatePath);
            }

            String outputPath = global.getOutputPath();
            File dirOutput = ResourceHelper.getDir(outputPath, PathType.FILESYSTEM);
            if (dirOutput == null || !dirOutput.exists()) {
                throw new CodeException("invalid outputPath" + outputPath);
            }
            templateConfig.setOutputDir(dirOutput.toPath());

        });
        return templateEngine;

    }

}
