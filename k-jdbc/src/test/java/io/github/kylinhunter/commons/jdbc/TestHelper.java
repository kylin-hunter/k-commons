package io.github.kylinhunter.commons.jdbc;

import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.execute.SqlReader;
import io.github.kylinhunter.commons.jdbc.meta.table.MysqlTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.redis.RedisConfig;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 00:26
 */
public class TestHelper {

  public static String TEST_TABLE = "k_junit_jdbc_role";
  public static String MONITOR_SCAN_TASK = "k_junit_table_monitor_scan_task";
  public static String MONITOR_BINLOG_TASK = "k_junit_table_monitor_binlog_task";

  public static void initTestSQl() {

    TableReader tableReader = new MysqlTableReader();

    if (!tableReader.exist("", TestHelper.TEST_TABLE)) {
      SqlExecutor sqlExecutor = tableReader.getSqlExecutor();
      List<String> sqlLines = SqlReader.readSqls(
          "io/github/kylinhunter/commons/jdbc/test.sql");
      sqlExecutor.executeBatch(sqlLines, true);
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