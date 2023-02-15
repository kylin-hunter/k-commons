package io.github.kylinhunter.commons.generator;

import java.util.List;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.context.Handler;
import io.github.kylinhunter.commons.generator.context.bean.CodeContext;
import io.github.kylinhunter.commons.generator.context.bean.ModuleContext;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.template.TemplateExecutor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 22:10
 **/
public class AutoCodeGenerator {
    private CodeContext codeContext = new CodeContext();
    private TemplateExecutor templateExecutor = CF.get(TemplateExecutor.class);


    public AutoCodeGenerator() {
        init();
    }

    /**
     * @return void
     * @title init
     * @description
     * @author BiJi'an
     * @date 2023-02-12 01:16
     */
    public void init() {
        List<Handler> handlers = CF.getAll(Handler.class);

        for (Handler handler : handlers) {
            handler.handle(codeContext);
        }
        initTemplateContexts();
        initModuleContexts();

    }

    /**
     * @return void
     * @title initTemplateContexts
     * @description
     * @author BiJi'an
     * @date 2023-02-12 01:16
     */
    private void initTemplateContexts() {

    }

    private void initModuleContexts() {

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


