package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;

import lombok.Data;

@Data
public class ModuleInfos {
    protected String databaseName;
    protected List<ModuleInfo> list;

}
