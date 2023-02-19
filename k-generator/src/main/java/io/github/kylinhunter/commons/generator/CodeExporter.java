package io.github.kylinhunter.commons.generator;

import java.util.Map;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.context.bean.Module;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContexts;
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
    public void export(TemplateContexts templateContexts) {
        for (TemplateContext templateContext : templateContexts.getAll()) {
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
        Module module = templateContext.getModule();
        TemplateConfig templateConfig = templateContext.getTemplateConfig();
        String templateName = templateConfig.getName();
        TemplateStrategy strategy = templateConfig.getStrategy();
        String templateEncoding = strategy.getTemplateEncoding();
        String outputEncoding = strategy.getOutputEncoding();
        log.info("output module=>{}/template={}", module.getName(), templateName);
        Map<String, Object> context = templateContext.getContext();

        context.forEach((k, v) -> {
            log.info(k + ":" + v);
        });
        templateExecutor.putContext(context);
        templateExecutor.tmplate(templateName, templateEncoding)
                .outputRelativePath(module.getName() + ".html").encoding(outputEncoding).build();
        templateExecutor.output(System.out::println);

    }
}
