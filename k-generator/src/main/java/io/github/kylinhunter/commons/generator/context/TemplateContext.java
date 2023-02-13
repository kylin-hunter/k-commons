package io.github.kylinhunter.commons.generator.context;

import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-14 00:57
 **/
@Data
public class TemplateContext {
    private TemplateConfig templateConfig;
    private String name;

    public TemplateContext(Global global, TemplateConfig templateConfig) {
        this.templateConfig = templateConfig;
        this.name = templateConfig.getName();

    }
}
