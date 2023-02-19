package io.github.kylinhunter.commons.generator.config.bean;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Module {
    @EqualsAndHashCode.Include
    private String name;
    protected String databaseName;
    private Map<String, Object> context = Maps.newHashMap();
    private Table table;

    public void merge(Modules modules) {
        if (!StringUtils.isEmpty(databaseName)) {
            this.databaseName = modules.getDatabaseName();
        }
    }
}
