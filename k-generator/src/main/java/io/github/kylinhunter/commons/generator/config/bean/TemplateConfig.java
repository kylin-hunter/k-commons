package io.github.kylinhunter.commons.generator.config.bean;

import org.springframework.beans.BeanUtils;

import lombok.Data;

@Data
public class TemplateConfig {
    private boolean enabled = true;
    private String name;
    private TemplateStrategy strategy;



}
