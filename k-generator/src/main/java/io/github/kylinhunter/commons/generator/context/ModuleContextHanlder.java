package io.github.kylinhunter.commons.generator.context;

import java.util.List;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Module;
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
@C
@RequiredArgsConstructor
public class ModuleContextHanlder implements Handler {
    private final Config config;
    @Override
    public void handle(CodeContext codeContext) {
        List<Module> modules = config.getModules();
        for (Module module : modules) {
            ModuleContext moduleContext = new ModuleContext(config.getGlobal(), module);
            codeContext.addModuleContext(moduleContext);

        }

        List<TemplateConfig> templateConfigs = config.getTemplates();
        for (TemplateConfig templateConfig : templateConfigs) {
            TemplateContext templateContext = new TemplateContext(config.getGlobal(), templateConfig);
            codeContext.addTemplateContext(templateContext);
        }
    }
}
