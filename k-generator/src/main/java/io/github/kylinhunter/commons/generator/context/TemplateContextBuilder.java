package io.github.kylinhunter.commons.generator.context;

import java.util.List;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.context.bean.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.ModuleInfos;
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
        ModuleInfos moduleInfos = moduleInfoReader.read();
        TemplateContexts templateContexts = new TemplateContexts();
        List<TemplateConfig> templates = config.getTemplates();
        for (TemplateConfig templateConfig : templates) {
            for (ModuleInfo moduleInfo : moduleInfos.getAll()) {
                templateContexts.add(toTemplateContext(moduleInfo, templateConfig));
            }
        }
        return templateContexts;
    }

    /**
     * @param moduleInfo     moduleContext
     * @param templateConfig templateConfig
     * @return io.github.kylinhunter.commons.generator.context.bean.TemplateContext
     * @title toTemplateContext
     * @description
     * @author BiJi'an
     * @date 2023-02-18 00:23
     */
    public TemplateContext toTemplateContext(ModuleInfo moduleInfo, TemplateConfig templateConfig) {
        TemplateContext templateContext = new TemplateContext(moduleInfo, templateConfig);
        ;

        return templateContext;

    }
}
