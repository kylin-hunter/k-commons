package io.github.kylinhunter.commons.generator;

import java.util.List;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.context.CodeContext;
import io.github.kylinhunter.commons.generator.context.ModuleContext;
import io.github.kylinhunter.commons.generator.context.TemplateContext;
import io.github.kylinhunter.commons.template.TemplateExecutor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 22:10
 **/
public class AutoCodeGenerator {
    private CodeContext codeContext = new CodeContext();
    private TemplateExecutor templateExecutor = CF.get(TemplateExecutor.class);
    private Config config = CF.get(Config.class);

    public AutoCodeGenerator() {
        init();
    }

    /**
     * @return void
     * @title init
     * @description
     * @author BiJi'an
     * @date 2023-02-14 01:16
     */
    public void init() {
        initTemplateContexts();
        initModuleContexts();

    }

    /**
     * @return void
     * @title initTemplateContexts
     * @description
     * @author BiJi'an
     * @date 2023-02-14 01:16
     */
    private void initTemplateContexts() {
        List<TemplateConfig> templateConfigs = config.getTemplates();
        for (TemplateConfig templateConfig : templateConfigs) {
            TemplateContext templateContext = new TemplateContext(config.getGlobal(), templateConfig);
            codeContext.addTemplateContext(templateContext);
        }
    }

    private void initModuleContexts() {
        List<Module> modules = config.getModules();
        for (Module module : modules) {
            ModuleContext moduleContext = new ModuleContext(config.getGlobal(), module);
            codeContext.addModuleContext(moduleContext);

        }
    }

    public void output() {
        for (ModuleContext moduleContext : codeContext.getModuleContexts()) {
            output(moduleContext);
        }

    }

    public void output(ModuleContext moduleContext) {
        System.out.println(moduleContext.getModule());
        List<TemplateContext> templateContexts = codeContext.getTemplateContexts();
        for (TemplateContext template : templateContexts) {

            TemplateStrategy strategy = template.getTemplateConfig().getStrategy();
            templateExecutor.tmplate(template.getName(), strategy.getEncoding(), strategy.getExtension())
                    .outputRelativePath(
                            "output3_result1"
                                    + ".html").encoding(strategy.getEncoding()).build();
            templateExecutor.output(System.out::println);
        }
    }

    public void execute() {
        output();
    }
}


