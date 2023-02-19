package io.github.kylinhunter.commons.generator.context.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.generator.config.bean.ModuleInfo;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
public class Module {
    private String name;
    private String databaseName;
    private Map<String, Object> context = Maps.newHashMap();
    private Table table;

    public Module(ModuleInfo moduleInfo) {
        this.name = moduleInfo.getName();
        this.databaseName = moduleInfo.getDatabaseName();
        this.context = moduleInfo.getContext();
    }
}
