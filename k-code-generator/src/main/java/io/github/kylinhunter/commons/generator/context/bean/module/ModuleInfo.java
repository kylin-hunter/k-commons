package io.github.kylinhunter.commons.generator.context.bean.module;

import io.github.kylinhunter.commons.generator.config.bean.Database;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ModuleInfo {
  @EqualsAndHashCode.Include private String name;
  private Database database;
  private TableInfo tableInfo;
  private Module module;

  public ModuleInfo(Database database, Module module, TableInfo tableInfo) {
    this.database = database;
    this.module = module;
    this.name = module.getName();
    this.tableInfo = tableInfo;
  }
}
