package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-23 00:01
 **/
@Data
public class Config {
    private Global global;
    private List<Module> modules;
    private CommonStrategy strategy;
    private List<TemplateConfig> templates;
}
