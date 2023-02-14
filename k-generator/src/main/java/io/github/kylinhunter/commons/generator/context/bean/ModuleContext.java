package io.github.kylinhunter.commons.generator.context.bean;

import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
public class ModuleContext {
    private Module module;
    private String name;

    public ModuleContext(Global global, Module module) {
        this.module = module;
        this.name = module.getName();

    }
}
