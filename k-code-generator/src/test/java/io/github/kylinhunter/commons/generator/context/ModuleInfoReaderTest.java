package io.github.kylinhunter.commons.generator.context;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.github.kylinhunter.commons.generator.TestHelper;
import io.github.kylinhunter.commons.generator.config.bean.Database;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.generator.context.bean.module.ModuleInfo;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ModuleInfoReaderTest {

  @Test
  void read() throws SQLException {
    DataSource dataSource = TestHelper.mockDataSource();
    ModuleInfoReader moduleInfoReader = new ModuleInfoReader(dataSource);
    Module module = new Module();
    Database database = new Database();
    module.setDatabase(database);
    Table table = new Table();
    module.setTable(table);
    module.setName("moduleName");

    TableReader tableReader = Mockito.mock(TableReader.class);
    ColumnReader columnReader = Mockito.mock(ColumnReader.class);
    ReflectUtils.setField(moduleInfoReader, "tableReader", tableReader);
    ReflectUtils.setField(moduleInfoReader, "columnReader", columnReader);

    TableMeta tableMeta = new TableMeta();
    when(tableReader.getTableMetaData(any(), any())).thenReturn(tableMeta);
    ColumnMetas columnMetas = new ColumnMetas();
    ColumnMeta columnMeta = new ColumnMeta();
    columnMetas.addColumnMeta(columnMeta);
    when(columnReader.getColumnMetaData(any(), any())).thenReturn(columnMetas);
    ModuleInfo moduleInfo = moduleInfoReader.read(module);
    Assertions.assertNotNull(moduleInfo);


  }
}