package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class Module {
    private String name;
    private String table;
    private List<String> tableSkipColumns = Lists.newArrayList();

}
