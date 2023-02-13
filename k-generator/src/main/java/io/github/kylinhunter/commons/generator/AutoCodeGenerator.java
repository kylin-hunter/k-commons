package io.github.kylinhunter.commons.generator;

import java.io.File;
import java.util.List;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.config.bean.CommonStrategy;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.context.CodeContext;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.template.TemplateExecutor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 22:10
 **/
public class AutoCodeGenerator {
    CodeContext codeContext = new CodeContext();
    TemplateExecutor templateExecutor;
    private Config config = CF.get(Config.class);

    public AutoCodeGenerator() {

        CodeTemplateEngine codeTemplateEngine = new CodeTemplateEngine();
        this.templateExecutor = codeTemplateEngine.createTemplateExecutor();
        init();
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
        List<Module> modules = config.getModules();
        for (Module module : modules) {
            output(module);
        }

    }

    public void output(Module module) {
        CommonStrategy commonStrategy = config.getGlobal().getStrategy();

        List<TemplateConfig> templates = config.getTemplates();
        for (TemplateConfig template : templates) {
            TemplateStrategy strategy = template.getStrategy();
            templateExecutor.tmplate(template.getName(), commonStrategy.getEncoding(), commonStrategy.getExtension())
                    .outputRelativePath(
                            "output3_result1"
                                    + ".html").encoding(commonStrategy.getEncoding()).build();
            templateExecutor.output(System.out::println);
        }
    }

    public void execute() {
        output();
    }
}


