package io.github.kylinhunter.commons.generator;

import java.io.File;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.config.ConfigParser;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.context.CodeContext;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.template.Engines;
import io.github.kylinhunter.commons.template.TemplateEngine;
import io.github.kylinhunter.commons.template.TemplateExecutor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 22:10
 **/
public class AutoCodeGenerator {
    CodeContext codeContext = new CodeContext();
    TemplateEngine templateEngine = CF.get(Engines.VELOCITY);
    TemplateExecutor templateExecutor;

    private void loadConfig() {
        ConfigParser configParser = new ConfigParser();
        Config config = configParser.load();
        codeContext.setConfig(config);

        String templatePath = config.getGlobal().getTemplatePath();
        File dir = ResourceHelper.getDir(templatePath);
        File[] files = dir.listFiles();
        for (File file : files) {
            codeContext.getTemplateFiles().add(file);
        }

    }

    public void custom(String key, Object value) {
        codeContext.getContexts().put(key, value);
    }

    public void initContext() {

    }

    public void initTemplateEngine() {

        Config generatorConfig = codeContext.getConfig();

        templateEngine.customize(config -> {
            String outputPathPath = generatorConfig.getGlobal().getOutputPathPath();
            config.setDefaultOutputDir(ResourceHelper.getDir(outputPathPath).toPath());

        });

        this.templateExecutor = templateEngine.createTemplateExecutor();
        initContext();
        codeContext.getContexts().forEach((k, v) -> {
            templateExecutor.putContext(k, v);
        });
    }

    public void execute() {
        initTemplateEngine();
        output();
    }

    public void output() {
        templateExecutor.tmplate("").outputRelativePath("output3_result1.html").encoding("UTF-8").build();
        templateExecutor.output(System.out::println);
    }
}


