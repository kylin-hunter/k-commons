package io.github.kylinhunter.commons.generator;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.context.TemplateContextBuilder;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContexts;

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
        TemplateContexts templateContexts = templateContextBuilder.build();
        codeExporter.export(templateContexts);
    }
}


