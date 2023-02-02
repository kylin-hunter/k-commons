package io.github.kylinhunter.commons.generator.config.bean;

import lombok.Data;

@Data
public class ModuleConfig {
    private String name;
    private Strategy strategy;
    private TemplateConfigs templates;
}
