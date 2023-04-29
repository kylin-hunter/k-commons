package io.github.kylinhunter.commons.jdbc.meta.parser.imp;

import com.mysql.cj.MysqlType;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.jdbc.meta.parser.ColumnParser;
import io.github.kylinhunter.commons.reflect.ClassUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@C
public class ColumnParserMysql implements ColumnParser {

  /**
   * @see ColumnParser#calJavaClass(int)
   */
  public Class<?> calJavaClass(int dataType) {
    try {
      MysqlType mysqlType = MysqlType.getByJdbcType(dataType);
      String className = mysqlType.getClassName();
      if (!StringUtils.isEmpty(className)) {
        return ClassUtil.loadClass(className);
      }
    } catch (Exception e) {
      throw new InitException("can't get javaClass", e);
    }
    return null;
  }
}
