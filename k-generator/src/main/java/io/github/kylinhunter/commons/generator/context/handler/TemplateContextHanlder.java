package io.github.kylinhunter.commons.generator.context.handler;

import java.util.List;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.context.bean.CodeContext;
import io.github.kylinhunter.commons.generator.context.bean.ModuleContext;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:24
 **/
@C(order = 2)
@RequiredArgsConstructor
public class TemplateContextHanlder implements ContextHandler {

    @CSet
    private Config config;

    @Override
    public void handle(CodeContext codeContext) {
        List<TemplateConfig> templates = config.getTemplates();
        for (TemplateConfig templateConfig : templates) {
            for (ModuleContext moduleContext : codeContext.getModuleContexts()) {
                TemplateConfig templateConfig = new TemplateConfig();
                TemplateContext templateContext=new TemplateContext(templateConfig);

                for (ModuleContext moduleContext : codeContext.getModuleContexts()) {

                }

            }

        }


    }

    public void handle(CodeContext codeContext) {

    }
}
