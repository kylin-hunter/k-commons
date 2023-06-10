package io.github.kylinhunter.commons.generator.context;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Database;
import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.generator.context.bean.clazz.FieldInfo;
import io.github.kylinhunter.commons.generator.function.ExpressionExecutor;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.name.SnakeToCamelUtils;
import io.github.kylinhunter.commons.util.ObjectValues;
import java.util.Map;
import org.apache.commons.lang3.ClassUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-26 19:04
 */
@C
public class FieldInfoConvertor {
  @CSet private ExpressionExecutor expressionExecutor;

  /**
   * @param columnMeta columnMeta
   * @param table table
   * @param database database
   * @return io.github.kylinhunter.commons.generator.context.bean.clazz.FieldInfo
   * @title convert
   * @description
   * @author BiJi'an
   * @date 2023-02-26 10:44
   */
  public FieldInfo convert(ColumnMeta columnMeta, Table table, Database database) {

    String fieldType = convertFieldType(columnMeta, table, database);
    FieldInfo fieldInfo = new FieldInfo();
    fieldInfo.setType(ClassUtils.getShortClassName(fieldType));
    fieldInfo.setFullType(fieldType);
    fieldInfo.setName(SnakeToCamelUtils.convert(columnMeta.getColumnName()));
    fieldInfo.setColumnName(columnMeta.getColumnName());
    fieldInfo.setComment(columnMeta.getRemarks());
    return fieldInfo;
  }

  public boolean support(String type, ColumnMeta columnMeta) {
    Map<String, Object> env = MapUtils.newHashMap();
    env.put("type", columnMeta.getTypeName().toLowerCase());
    env.put("size", columnMeta.getColumnSize());
    env.put("precision", columnMeta.getDecimalDigits());
    return ObjectValues.getBoolean(expressionExecutor.execute(type, env), false);
  }

  /**
   * @param columnMeta columnMeta
   * @return java.lang.Class<?>
   * @title convert
   * @description
   * @author BiJi'an
   * @date 2023-02-26 19:07
   */
  private String convertFieldType(ColumnMeta columnMeta, Table table, Database database) {
    String fieldType = table.getColumnType(columnMeta.getColumnName());
    if (!StringUtil.isEmpty(fieldType)) {
      return fieldType;
    }

    fieldType = database.getSqlType(columnMeta.getTypeName().toLowerCase());
    if (!StringUtil.isEmpty(fieldType)) {
      return fieldType;
    }
    Map<String, String> sqlTypes = database.getSqlTypes();

    for (Map.Entry<String, String> entry : sqlTypes.entrySet()) {
      String sqlType = entry.getKey();
      if (support(sqlType, columnMeta)) {
        return entry.getValue();
      }
    }
    return columnMeta.getJavaClass().getName();
  }
}
