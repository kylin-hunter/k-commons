package io.github.kylinhunter.commons.generator;

import java.io.File;
import java.util.List;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.config.ConfigParser;
import io.github.kylinhunter.commons.generator.config.bean.CommonStrategy;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
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
    private Config config;

    private void loadConfig() {
        ConfigParser configParser = new ConfigParser();
        this.config = configParser.load();
        codeContext.setConfig(config);

    }

    public void initTemplateEngine() {
        loadConfig();
        Config generatorConfig = codeContext.getConfig();

        templateEngine.customize(config -> {
            Global global = generatorConfig.getGlobal();
            String outputPathPath = global.getOutputPath();
            config.setOutputDir(ResourceHelper.getDir(outputPathPath).toPath());

            String templatePath = global.getTemplatePath();
            config.setTemplateDir(ResourceHelper.getDir(templatePath).toPath());

        });

        this.templateExecutor = templateEngine.createTemplateExecutor();
        codeContext.getContexts().forEach((k, v) -> {
            templateExecutor.putContext(k, v);
        });
    }

    public void init() {
        String templatePath = config.getGlobal().getTemplatePath();
        File dir = ResourceHelper.getDir(templatePath);
        File[] files = dir.listFiles();
        for (File file : files) {
            codeContext.getTemplateFiles().add(file);
        }

    }

    public void output() {
        CommonStrategy strategy = config.getStrategy();
        List<TemplateConfig> templates = config.getTemplates();
        for (TemplateConfig template : templates) {
            templateExecutor.tmplate(template.getName(), strategy.getEncoding(), strategy.getExtension())
                    .outputRelativePath(
                            "output3_result1"
                                    + ".html").encoding("UTF-8").build();
            templateExecutor.output(System.out::println);
        }

    }

    public void execute() {
        initTemplateEngine();
        output();
    }
}


