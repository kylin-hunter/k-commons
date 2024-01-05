package io.github.kylinhunter.commons.generator;

import static org.mockito.ArgumentMatchers.any;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.generator.config.ConfigReader;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.generator.context.ModuleInfoReader;
import io.github.kylinhunter.commons.generator.context.TemplateContextBuilder;
import io.github.kylinhunter.commons.generator.context.bean.module.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.module.TableInfo;
import io.github.kylinhunter.commons.io.file.FileUtil;
import io.github.kylinhunter.commons.io.file.TmpDirUtils;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class CodeGeneratorTest {


  @Test
  void execute() throws NoSuchFieldException, SQLException {
    File sourceDir = TmpDirUtils.getUserDir("generator/src");
    File compileOutputDir = TmpDirUtils.getUserDir("generator/classes");

    DataSource dataSource = TestHelper.mockDataSource();

    CodeGenerator codeGenerator = new CodeGenerator(dataSource);
    Config config = new ConfigReader().load();

    ModuleInfoReader moduleInfoReader = mockModuleInfoReader();
    codeGenerator.setTemplateContextBuilder(new TemplateContextBuilder(config, moduleInfoReader));
    FileUtil.cleanDirectoryQuietly(compileOutputDir);

    codeGenerator.execute();
    TestCodeGenerator.verify(compileOutputDir, sourceDir);

  }

  public ModuleInfoReader mockModuleInfoReader() {
    ModuleInfoReader moduleInfoReader = Mockito.mock(ModuleInfoReader.class);

    Mockito.when(moduleInfoReader.read(any())).thenAnswer(invocation -> {
      Module module = invocation.getArgument(0);

      if (module.getName().equals("User")) {
        return new ModuleInfo(module, userTableInf());
      } else if (module.getName().equals("Role")) {
        return new ModuleInfo(module, roleTableInfo());
      }
      throw new RuntimeException("not support module: " + module.getName());
    });

    return moduleInfoReader;

  }

  /**
   * @return io.github.kylinhunter.commons.generator.context.bean.module.TableInfo
   * @title userTableInf
   * @description userTableInf
   * @author BiJi'an
   * @date 2023-12-17 19:20
   */
  private TableInfo userTableInf() {
    TableInfo tableInfo = new TableInfo();

    Table table = new Table();
    tableInfo.setTable(table);
    table.setName(TestHelper.TABLE_USER);
    table.setSkipColumns(ListUtils.newArrayList());
    HashMap<String, String> columnTypes = MapUtils.newHashMap();
    columnTypes.put("role_id", Integer.class.getName());
    table.setColumnTypes(columnTypes);

    TableMeta tableMeta = new TableMeta();
    tableInfo.setTableMeta(tableMeta);
    tableMeta.setName(TestHelper.TABLE_USER);
    tableMeta.setRemarks("the user");

    List<ColumnMeta> columnMetas = ListUtils.newArrayList();
    tableInfo.setColumnMetas(columnMetas);
    ColumnMeta columnMeta1 = new ColumnMeta();

    columnMetas.add(columnMeta1);
    columnMeta1.setTableName(TestHelper.TABLE_USER);
    columnMeta1.setColumnName("id");
    columnMeta1.setRemarks("primary unique id");
    columnMeta1.setJavaClass(Long.class);
    columnMeta1.setColumnSize(19);
    columnMeta1.setDecimalDigits(0);
    columnMeta1.setTypeName("BIGINT");

    ColumnMeta columnMeta2 = new ColumnMeta();

    columnMetas.add(columnMeta2);
    columnMeta2.setTableName(TestHelper.TABLE_USER);
    columnMeta2.setColumnName("name");
    columnMeta2.setRemarks("user name");
    columnMeta2.setJavaClass(String.class);
    columnMeta2.setColumnSize(64);
    columnMeta2.setDecimalDigits(0);
    columnMeta2.setTypeName("VARCHAR");

    ColumnMeta columnMeta3 = new ColumnMeta();

    columnMetas.add(columnMeta3);
    columnMeta3.setTableName(TestHelper.TABLE_USER);
    columnMeta3.setColumnName("birth");
    columnMeta3.setRemarks("birthday");
    columnMeta3.setJavaClass(Timestamp.class);
    columnMeta3.setColumnSize(19);
    columnMeta3.setDecimalDigits(0);
    columnMeta3.setTypeName("DATETIME");

    ColumnMeta columnMeta4 = new ColumnMeta();

    columnMetas.add(columnMeta4);
    columnMeta4.setTableName(TestHelper.TABLE_USER);
    columnMeta4.setColumnName("leave_company_time");
    columnMeta4.setRemarks("the time leave company");
    columnMeta4.setJavaClass(Timestamp.class);
    columnMeta4.setColumnSize(19);
    columnMeta4.setDecimalDigits(0);
    columnMeta4.setTypeName("TIMESTAMP");

    ColumnMeta columnMeta5 = new ColumnMeta();

    columnMetas.add(columnMeta5);
    columnMeta5.setTableName(TestHelper.TABLE_USER);
    columnMeta5.setColumnName("join_company_date");
    columnMeta5.setRemarks("what time to join the company");
    columnMeta5.setJavaClass(Date.class);
    columnMeta5.setColumnSize(10);
    columnMeta5.setDecimalDigits(0);
    columnMeta5.setTypeName("DATE");

    ColumnMeta columnMeta6 = new ColumnMeta();

    columnMetas.add(columnMeta6);
    columnMeta6.setTableName(TestHelper.TABLE_USER);
    columnMeta6.setColumnName("work_time");
    columnMeta6.setRemarks("what time to work ervery moring");
    columnMeta6.setJavaClass(Time.class);
    columnMeta6.setColumnSize(8);
    columnMeta6.setDecimalDigits(0);
    columnMeta6.setTypeName("TIME");

    ColumnMeta columnMeta7 = new ColumnMeta();
    columnMetas.add(columnMeta7);
    columnMeta7.setTableName(TestHelper.TABLE_USER);
    columnMeta7.setColumnName("work_hours");
    columnMeta7.setRemarks("how many hours to work everyday");
    columnMeta7.setJavaClass(Integer.class);
    columnMeta7.setColumnSize(10);
    columnMeta7.setDecimalDigits(0);
    columnMeta7.setTypeName("INT");

    ColumnMeta columnMeta8 = new ColumnMeta();
    columnMetas.add(columnMeta8);
    columnMeta8.setTableName(TestHelper.TABLE_USER);
    columnMeta8.setColumnName("age");
    columnMeta8.setRemarks("age");
    columnMeta8.setJavaClass(Integer.class);
    columnMeta8.setColumnSize(5);
    columnMeta8.setDecimalDigits(0);
    columnMeta8.setTypeName("SMALLINT");

    ColumnMeta columnMeta9 = new ColumnMeta();
    columnMetas.add(columnMeta9);
    columnMeta9.setTableName(TestHelper.TABLE_USER);
    columnMeta9.setColumnName("height");
    columnMeta9.setRemarks("height");
    columnMeta9.setJavaClass(Float.class);
    columnMeta9.setColumnSize(9);
    columnMeta9.setDecimalDigits(2);
    columnMeta9.setTypeName("FLOAT");

    ColumnMeta columnMeta10 = new ColumnMeta();
    columnMetas.add(columnMeta10);
    columnMeta10.setTableName(TestHelper.TABLE_USER);
    columnMeta10.setColumnName("weight");
    columnMeta10.setRemarks("weight");
    columnMeta10.setJavaClass(Double.class);
    columnMeta10.setColumnSize(19);
    columnMeta10.setDecimalDigits(2);
    columnMeta10.setTypeName("DOUBLE");

    ColumnMeta columnMeta11 = new ColumnMeta();
    columnMetas.add(columnMeta11);
    columnMeta11.setTableName(TestHelper.TABLE_USER);
    columnMeta11.setColumnName("money_income");
    columnMeta11.setRemarks("all money income");
    columnMeta11.setJavaClass(BigDecimal.class);
    columnMeta11.setColumnSize(20);
    columnMeta11.setDecimalDigits(2);
    columnMeta11.setTypeName("DECIMAL");

    ColumnMeta columnMeta12 = new ColumnMeta();
    columnMetas.add(columnMeta12);
    columnMeta12.setTableName(TestHelper.TABLE_USER);
    columnMeta12.setColumnName("money_spend");
    columnMeta12.setRemarks("the money spent");
    columnMeta12.setJavaClass(BigDecimal.class);
    columnMeta12.setColumnSize(19);
    columnMeta12.setDecimalDigits(0);
    columnMeta12.setTypeName("DECIMAL");

    ColumnMeta columnMeta13 = new ColumnMeta();
    columnMetas.add(columnMeta13);
    columnMeta13.setTableName(TestHelper.TABLE_USER);
    columnMeta13.setColumnName("address");
    columnMeta13.setRemarks("address");
    columnMeta13.setJavaClass(String.class);
    columnMeta13.setColumnSize(512);
    columnMeta13.setDecimalDigits(0);
    columnMeta13.setTypeName("VARCHAR");

    ColumnMeta columnMeta14 = new ColumnMeta();
    columnMetas.add(columnMeta14);
    columnMeta14.setTableName(TestHelper.TABLE_USER);
    columnMeta14.setColumnName("delete_flag");
    columnMeta14.setRemarks("is deleted");
    columnMeta14.setJavaClass(Boolean.class);
    columnMeta14.setColumnSize(1);
    columnMeta14.setDecimalDigits(0);
    columnMeta14.setTypeName("BIT");

    ColumnMeta columnMeta15 = new ColumnMeta();
    columnMetas.add(columnMeta15);
    columnMeta15.setTableName(TestHelper.TABLE_USER);
    columnMeta15.setColumnName("sex");
    columnMeta15.setRemarks("0 unkown 1 male 2 female");
    columnMeta15.setJavaClass(Integer.class);
    columnMeta15.setColumnSize(3);
    columnMeta15.setDecimalDigits(0);
    columnMeta15.setTypeName("TINYINT");

    ColumnMeta columnMeta16 = new ColumnMeta();
    columnMetas.add(columnMeta16);
    columnMeta16.setTableName(TestHelper.TABLE_USER);
    columnMeta16.setColumnName("role_id");
    columnMeta16.setRemarks("角色 ID");
    columnMeta16.setJavaClass(Long.class);
    columnMeta16.setColumnSize(19);
    columnMeta16.setDecimalDigits(0);
    columnMeta16.setTypeName("BIGINT");

    return tableInfo;

  }

  /**
   * @return io.github.kylinhunter.commons.generator.context.bean.module.TableInfo
   * @title roleTableInfo
   * @description roleTableInfo
   * @author BiJi'an
   * @date 2023-12-17 19:20
   */
  private TableInfo roleTableInfo() {

    TableInfo tableInfo = new TableInfo();

    Table table = new Table();
    tableInfo.setTable(table);
    table.setName(TestHelper.TABLE_ROLE);
    table.setSkipColumns(ListUtils.newArrayList());
    table.setColumnTypes(MapUtils.newHashMap());

    TableMeta tableMeta = new TableMeta();
    tableInfo.setTableMeta(tableMeta);
    tableMeta.setName(TestHelper.TABLE_ROLE);
    tableMeta.setRemarks("the roles");

    List<ColumnMeta> columnMetas = ListUtils.newArrayList();
    tableInfo.setColumnMetas(columnMetas);
    ColumnMeta columnMeta1 = new ColumnMeta();

    columnMetas.add(columnMeta1);
    columnMeta1.setTableName(TestHelper.TABLE_USER);
    columnMeta1.setColumnName("id");
    columnMeta1.setRemarks("primary unique id");
    columnMeta1.setJavaClass(Long.class);
    columnMeta1.setColumnSize(19);
    columnMeta1.setDecimalDigits(0);
    columnMeta1.setTypeName("BIGINT");

    ColumnMeta columnMeta2 = new ColumnMeta();

    columnMetas.add(columnMeta2);
    columnMeta2.setTableName(TestHelper.TABLE_USER);
    columnMeta2.setColumnName("name");
    columnMeta2.setRemarks("role name");
    columnMeta2.setJavaClass(String.class);
    columnMeta2.setColumnSize(64);
    columnMeta2.setDecimalDigits(0);
    columnMeta2.setTypeName("VARCHAR");

    return tableInfo;

  }


}
