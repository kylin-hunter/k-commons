package io.github.kylinhunter.commons.generator.configuration;

import java.io.File;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CC;
import io.github.kylinhunter.commons.generator.config.ConfigReader;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.generator.config.bean.Output;
import io.github.kylinhunter.commons.generator.exception.CodeException;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.ResourceHelper.PathType;
import io.github.kylinhunter.commons.template.TemplateEngine;
import io.github.kylinhunter.commons.template.config.OutputConfig;
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
            templateConfig.setTemplatePath(dirTemplate.toPath());
            if (dirTemplate == null || !dirTemplate.exists()) {
                throw new CodeException("invalid templatePath" + templatePath);
            }

            Output output = global.getOutput();
            String outputPath = output.getPath();
            OutputConfig outputConfig = templateConfig.getOutputConfig();
            outputConfig.setAutoCreate(output.isAutoCreate());
            outputConfig.setAutoClean(output.isAutoClean());
            outputConfig.setOutputPath(outputPath);

        });
        return templateEngine;

    }

}
