package io.github.kylinhunter.commons.generator;

import java.util.List;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.context.TemplateContextBuilder;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 22:10
 **/
public class CodeGenerator {
    private TemplateContextBuilder templateContextBuilder = CF.get(TemplateContextBuilder.class);
    private CodeExporter codeExporter = CF.get(CodeExporter.class);

    /**
     * @return void
     * @title execute
     * @description
     * @author BiJi'an
     * @date 2023-02-11 23:38
     */
    public void execute() {
        List<TemplateContext> templateContexts = templateContextBuilder.calculateContext();
        codeExporter.export(templateContexts);
    }
}


