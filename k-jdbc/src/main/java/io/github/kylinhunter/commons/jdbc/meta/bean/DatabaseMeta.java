package io.github.kylinhunter.commons.jdbc.meta.bean;

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Data
public class DatabaseMeta {
  private String url;
  private String productName;
  private String version;
  private String driverName;
  private DbType dbType;

  public void setUrl(String url) {
    this.url = url;
    this.dbType = calDbType(url);
  }

  private DbType calDbType(String jdbcUrl) {

    ExceptionChecker.checkNotEmpty(jdbcUrl, "jdbcUrl can't be  empty");
    String lowerCaseJdbcUrl = jdbcUrl.toLowerCase();
    if (lowerCaseJdbcUrl.contains("mysql")) {
      return DbType.MYSQL;
    } else if (lowerCaseJdbcUrl.contains("oracle")) {
      return DbType.ORACLE;
    } else if (lowerCaseJdbcUrl.contains("sqlserver")) {
      return DbType.SQL_SERVER;
    } else {
      return DbType.OTHERS;
    }
  }
}
