package io.github.kylinhunter.commons.generator.config.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-23 00:01
 **/
@Data
public class Config {
    private Global global;
    private Modules modules;
    private Templates templates;
}
