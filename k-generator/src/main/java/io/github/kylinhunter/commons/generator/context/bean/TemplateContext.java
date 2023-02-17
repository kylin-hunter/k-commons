package io.github.kylinhunter.commons.generator.context.bean;

import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
public class TemplateContext {
    private String name;

    public TemplateContext(TemplateConfig templateConfig) {
        this.name = templateConfig.getName();
    }
}
