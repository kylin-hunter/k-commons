package io.github.kylinhunter.commons.generator.context.bean;

import io.github.kylinhunter.commons.generator.config.bean.Module;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ModuleInfo {
    @EqualsAndHashCode.Include
    private String name;
    private String databaseName;
    private TableInfo tableInfo;

    public ModuleInfo(Module module, TableInfo tableInfo) {
        this.name = module.getName();
        this.databaseName = module.getDatabaseName();
        this.tableInfo = tableInfo;
    }
}
