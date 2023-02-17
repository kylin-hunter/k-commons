package io.github.kylinhunter.commons.generator;

import java.util.List;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.context.bean.CodeContext;
import io.github.kylinhunter.commons.generator.context.bean.ModuleContext;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.template.TemplateExecutor;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-10 01:29
 **/
@C
@RequiredArgsConstructor
public class CodeExporter {
    @CSet
    private TemplateExecutor templateExecutor;

    /**
     * @param codeContext codeContext
     * @return void
     * @title output
     * @description
     * @author BiJi'an
     * @date 2023-02-11 23:36
     */
    public void export(CodeContext codeContext) {
        for (ModuleContext moduleContext : codeContext.getModuleContexts()) {
            export(codeContext, moduleContext);
        }
    }

    /**
     * @param codeContext   codeContext
     * @param moduleContext moduleContext
     * @return void
     * @title output
     * @description
     * @author BiJi'an
     * @date 2023-02-11 23:36
     */
    private void export(CodeContext codeContext, ModuleContext moduleContext) {
        System.out.println(moduleContext.getModule());
        List<TemplateContext> templateContexts = codeContext.getTemplateContexts();
        for (TemplateContext template : templateContexts) {
            TemplateConfig templateConfig = template.getTemplateConfig();
            TemplateStrategy strategy = templateConfig.getStrategy();
            templateExecutor.tmplate(template.getName(), strategy.getEncoding(), strategy.getExtension())
                    .outputRelativePath(
                            "output3_result1"
                                    + ".html").encoding(strategy.getEncoding()).build();
            templateExecutor.output(System.out::println);
        }
    }
}
