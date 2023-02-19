package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class ModuleInfo {
    private String name;
    protected String databaseName;
    private Map<String, Object> context = Maps.newHashMap();
    private TableInfo table;

    public void merge(ModuleInfos moduleInfos) {
        if (!StringUtils.isEmpty(databaseName)) {
            this.databaseName = moduleInfos.getDatabaseName();
        }
    }
}
