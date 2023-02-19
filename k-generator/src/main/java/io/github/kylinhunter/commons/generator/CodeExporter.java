package io.github.kylinhunter.commons.generator;

import java.util.List;
import java.util.Map;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
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
        TemplateExecutor templateExecutor = templateEngine.createTemplateExecutor();
        Template template = templateContext.getTemplate();
        String templateName = template.getName();
        Module module = templateContext.getModule();
        String moduleName = module.getName();
        TemplateStrategy strategy = template.getStrategy();
        String templateEncoding = strategy.getTemplateEncoding();
        String outputEncoding = strategy.getOutputEncoding();
        log.info("output module=>{}/template={}", moduleName, templateName);
        Map<String, Object> context = templateContext.getContext();
        context.forEach((k, v) -> {
            log.info(k + ":" + v);
        });
        templateExecutor.putContext(context);
        String outputRelativePath = getOutputRelativePath(templateContext);
        templateExecutor.tmplate(templateName, templateEncoding)
                .outputRelativePath(outputRelativePath).extension("java").encoding(outputEncoding).build();
        templateExecutor.output(System.out::println);

    }

    public String getOutputRelativePath(TemplateContext templateContext) {

        return templateContext.getPackageName().replaceAll("\\.", "/") + "/" + templateContext.getClassName();

    }
}
