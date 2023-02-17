package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class ModuleTable {
    private String name;
    private List<String> skipColumns = Lists.newArrayList();

}
