package io.github.kylinhunter.commons.generator;

import java.util.List;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.generator.context.bean.clazz.ClassInfo;
import io.github.kylinhunter.commons.template.TemplateEngine;
import io.github.kylinhunter.commons.template.TemplateExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-10 01:29
 **/
@C
@RequiredArgsConstructor
@Slf4j
public class CodeExporter {
    @CSet
    private TemplateEngine templateEngine;

    /**
     * @param templateContexts templateInfos
     * @return void
     * @title output
     * @description
     * @author BiJi'an
     * @date 2023-02-11 23:36
     */
    public void export(List<TemplateContext> templateContexts) {
        for (TemplateContext templateContext : templateContexts) {
            templateContext.getContext().forEach((k, v) -> log.info("{}:{}", k, v));
            export(templateContext);
        }
    }

    /**
     * @param templateContext templateInfo
     * @return void
     * @title output
     * @description
     * @author BiJi'an
     * @date 2023-02-11 23:36
     */
    private void export(TemplateContext templateContext) {
        TemplateExecutor templateExecutor = templateEngine.createTemplateExecutor(templateContext.getContext());
        Template template = templateContext.getTemplate();
        Module module = templateContext.getModuleInfo().getModule();
        TemplateStrategy strategy = template.getStrategy();
        log.info("export module=>{}/template={}", module.getName(), template.getName());
        templateExecutor
                .tmplate(template.getName(), strategy.getTemplateEncoding())
                .outputRelativePath(getOutputRelativePath(templateContext))
                .extension(strategy.getExtension())
                .encoding(strategy.getOutputEncoding())
                .build();
        templateExecutor.output();

    }

    /**
     * @param templateContext templateContext
     * @return java.lang.String
     * @title getOutputRelativePath
     * @description
     * @author BiJi'an
     * @date 2023-02-19 00:14
     */
    public String getOutputRelativePath(TemplateContext templateContext) {
        ClassInfo classInfo = templateContext.getClassInfo();
        return classInfo.getPackageName().replaceAll("\\.", "/") + "/" + classInfo.getName();
    }
}
