package io.github.kylinhunter.commons.generator.context.bean.module;

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
    private TableInfo table;

    public ModuleInfo(Module module, TableInfo tableInfo) {
        this.name = module.getName();
        this.databaseName = module.getDatabaseName();
        this.table = tableInfo;
    }
}
