package io.github.kylinhunter.commons.generator.context;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Modules;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.constant.ContextConsts;
import io.github.kylinhunter.commons.generator.context.bean.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.generator.function.ExpressionExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:24
 **/
@C
@RequiredArgsConstructor
@Slf4j
public class TemplateContextBuilder {

    @CSet
    private Config config;
    @CSet
    private ModuleInfoReader moduleInfoReader;
    @CSet
    private ExpressionExecutor expressionExecutor;

    public List<TemplateContext> build() {
        List<TemplateContext> templateContexts = Lists.newArrayList();
        Modules modules = config.getModules();
        for (Module module : modules.getList()) {
            ModuleInfo moduleInfo = moduleInfoReader.read(module);
            for (Template template : config.getTemplates().getList()) {
                TemplateContext templateContext = new TemplateContext(module, template);
                templateContext.putContext(build(moduleInfo, module, template));
                templateContexts.add(templateContext);

            }

        }

        return templateContexts;
    }

    /**
     * @param moduleInfo moduleInfo
     * @param template   template
     * @return io.github.kylinhunter.commons.generator.context.bean.TemplateContext
     * @title toTemplateContext
     * @description
     * @author BiJi'an
     * @date 2023-02-18 00:23
     */
    private Map<String, Object> build(ModuleInfo moduleInfo, Module module, Template template) {
        Map<String, Object> context = Maps.newHashMap();
        context.put(ContextConsts.MODULE, moduleInfo);

        context.putAll(template.getContext());
        context.putAll(module.getContext());

        TemplateStrategy strategy = template.getStrategy();
        String packageName = strategy.getPackageName();
        String className = strategy.getClassName();
        context.put(ContextConsts.PACKAGE_NAME, expressionExecutor.execute(packageName, context));
        context.put(ContextConsts.CLASS_NAME, expressionExecutor.execute(className, context));
        context.forEach((k, v) -> log.info(k + ":" + v));
        return context;

    }

}
