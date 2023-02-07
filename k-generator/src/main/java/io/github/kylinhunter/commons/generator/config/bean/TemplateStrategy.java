package io.github.kylinhunter.commons.generator.config.bean;

import lombok.Data;

@Data
public class TemplateStrategy extends CommonStrategy {
    private String superClass;
    private String classNamePattern;

}
