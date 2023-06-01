package io.github.kylinhunter.commons.generator.context.bean.module;

import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 22:27
 */
@NoArgsConstructor
@Getter
@Setter
public class TableInfo {
  private Table table;
  private TableMeta tableMeta;
  private List<ColumnMeta> columnMetas;

  public TableInfo(Table table) {
    this.table = table;
  }
}
