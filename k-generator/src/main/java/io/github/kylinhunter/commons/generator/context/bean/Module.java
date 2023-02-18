package io.github.kylinhunter.commons.generator.context.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
public class Module {
    private String name;
    private String database;
    private Table table;

    public Module(io.github.kylinhunter.commons.generator.config.bean.ModuleInfo moduleInfo) {
        this.name = moduleInfo.getName();

    }
}
