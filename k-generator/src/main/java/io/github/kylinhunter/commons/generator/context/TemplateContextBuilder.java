package io.github.kylinhunter.commons.generator.context;

import java.util.List;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.constant.Contexts;
import io.github.kylinhunter.commons.generator.context.bean.Module;
import io.github.kylinhunter.commons.generator.context.bean.Modules;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContexts;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:24
 **/
@C(order = 2)
@RequiredArgsConstructor
public class TemplateContextBuilder {

    @CSet
    private Config config;
    @CSet
    private ModuleInfoReader moduleInfoReader;

    public TemplateContexts build() {
        Modules modules = moduleInfoReader.read();
        TemplateContexts templateContexts = new TemplateContexts();
        List<TemplateConfig> templates = config.getTemplates();
        for (TemplateConfig templateConfig : templates) {
            for (Module module : modules.getAll()) {
                templateContexts.add(toTemplateContext(module, templateConfig));
            }
        }
        return templateContexts;
    }

    /**
     * @param module         moduleContext
     * @param templateConfig templateConfig
     * @return io.github.kylinhunter.commons.generator.context.bean.TemplateContext
     * @title toTemplateContext
     * @description
     * @author BiJi'an
     * @date 2023-02-18 00:23
     */
    public TemplateContext toTemplateContext(Module module, TemplateConfig templateConfig) {
        TemplateContext templateContext = new TemplateContext(module, templateConfig);

        TemplateStrategy strategy = templateContext.getTemplateConfig().getStrategy();
        String packageName = strategy.getPackageName();
        String className = strategy.getClassName();
        templateContext.putContext(Contexts.PACKAGE_NAME, packageName);
        templateContext.putContext(Contexts.CLASS_NAME, className);
        return templateContext;

    }
}
