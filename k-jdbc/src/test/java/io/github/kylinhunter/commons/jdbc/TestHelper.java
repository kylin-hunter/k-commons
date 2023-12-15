package io.github.kylinhunter.commons.jdbc;

import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisConfig;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.execute.SqlReader;
import io.github.kylinhunter.commons.jdbc.meta.table.MysqlTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import java.util.List;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 00:26
 */
public class TestHelper {

  public static String MYSQL_JDBC_URL = "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
  public static String MYSQL_JDBC_URL2 = "jdbc:mysql://localhost:3307/kp?useUnicode=true"
      + "&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
  public static String ORACLE_JDBC_URL = "jdbc:oracle:thin:@localhost:1521:ORACLE?user=your_username&password=your_password";
  public static String SQLSERVER_JDBC_URL = "dbc:sqlserver://localhost:1433;DatabaseName=test;username=sa; password=passwd";

  public static String DATABASE = "kp";

  public static String MYSQL_USERNAME = "root";

  public static String MYSQL_PASSWORD = "root";

  public static String TEST_TABLE_ROLE = "k_junit_jdbc_role";

  public static String TEST_TABLE_TMP = "k_junit_tmp";
  public static String MONITOR_SCAN_TASK = "k_junit_table_monitor_scan_task";
  public static String MONITOR_BINLOG_TASK = "k_junit_table_monitor_binlog_task";

  public static String BINLOG_FILENAME = "binlog.000029";
  public static long BINLOG_POS = 1776364;

  public static String TEST_SQL = "io/github/kylinhunter/commons/jdbc/test_data.sql";


  public static void initTestSQl() {

    initTestSQl(null);
  }

  public static void initTestSQl(DataSource dataSource) {

    TableReader tableReader = dataSource != null ? new MysqlTableReader(dataSource) :
        new MysqlTableReader();

    if (!tableReader.exist("", TestHelper.TEST_TABLE_ROLE)) {
      SqlExecutor sqlExecutor = tableReader.getSqlExecutor();
      List<String> sqlLines = SqlReader.readSqls(TEST_SQL);
      sqlExecutor.executeBatch(true, sqlLines);
    }
  }

  public static RedisConfig getRedisConfig() {
    RedisConfig redisConfig = new RedisConfig();
    redisConfig.setHost("127.0.0.1");
    redisConfig.setPort(6379);
    redisConfig.setPassword("123456");
    return redisConfig;
  }
}