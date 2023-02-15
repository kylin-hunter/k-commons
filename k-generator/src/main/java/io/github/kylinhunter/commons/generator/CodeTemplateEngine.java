package io.github.kylinhunter.commons.generator;

import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.template.TemplateEngine;
import io.github.kylinhunter.commons.template.TemplateExecutor;
import io.github.kylinhunter.commons.template.velocity.VelocityTemplateEngine;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-04 20:25
 **/
@RequiredArgsConstructor
public class CodeTemplateEngine {
    private final Config config;

    public TemplateExecutor createTemplateExecutor() {
        TemplateEngine templateEngine = new VelocityTemplateEngine();
        templateEngine.customize(templateConfig -> {
            Global global = config.getGlobal();
            String outputPathPath = global.getOutputPath();
            templateConfig.setOutputDir(ResourceHelper.getDir(outputPathPath).toPath());

            String templatePath = global.getTemplatePath();
            templateConfig.setTemplateDir(ResourceHelper.getDir(templatePath).toPath());

        });
        return templateEngine.createTemplateExecutor();
    }

}
