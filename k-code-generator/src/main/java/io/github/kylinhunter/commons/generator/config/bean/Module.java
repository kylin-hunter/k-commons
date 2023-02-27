package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Module {
    @EqualsAndHashCode.Include
    private String name;
    protected Database database;
    private Map<String, Object> context = Maps.newHashMap();
    private Table table;

    public void merge(Modules modules) {
        this.database = modules.database;
    }
}
