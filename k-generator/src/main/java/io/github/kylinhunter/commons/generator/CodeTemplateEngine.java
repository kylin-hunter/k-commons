package io.github.kylinhunter.commons.generator;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.template.Engines;
import io.github.kylinhunter.commons.template.TemplateEngine;
import io.github.kylinhunter.commons.template.TemplateExecutor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-04 20:25
 **/
public class CodeTemplateEngine {
    private final Config config = CF.get(Config.class);
    private final  TemplateEngine templateEngine = CF.get(Engines.VELOCITY);

    public TemplateExecutor createTemplateExecutor() {
        templateEngine.customize(custom -> {
            Global global = config.getGlobal();
            String outputPathPath = global.getOutputPath();
            custom.setOutputDir(ResourceHelper.getDir(outputPathPath).toPath());

            String templatePath = global.getTemplatePath();
            custom.setTemplateDir(ResourceHelper.getDir(templatePath).toPath());

        });
        return templateEngine.createTemplateExecutor();
    }

}
