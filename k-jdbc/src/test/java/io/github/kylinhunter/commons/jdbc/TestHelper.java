package io.github.kylinhunter.commons.jdbc;

import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.ClusterRedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.JdkRedisCodec;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.RedisExecutor;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.SingleRedisConfig;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.redis.SingleRedisExecutor;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.execute.SqlReader;
import io.github.kylinhunter.commons.jdbc.meta.table.MysqlTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import io.github.kylinhunter.commons.jdbc.test.TestDataSourceHelper;
import java.sql.SQLException;
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

  public static String TEST_TABLE_ROLE1 = "k_junit_jdbc_role1";
  public static String TEST_TABLE_ROLE2 = "k_junit_jdbc_role2";

  public static String TEST_TABLE_TMP = "k_junit_tmp";
  public static String TEST_SCAN_TASK = "k_junit_table_monitor_scan";

  public static String TEST_SCAN_TASK2 = "k_junit_table_monitor_scan2";
  public static String TEST_BINLOG_TASK = "k_junit_table_monitor_binlog";

  public static String BINLOG_FILENAME = "binlog.000047";
  public static long BINLOG_POS = 0;

  public static String TEST_SQL = "io/github/kylinhunter/commons/jdbc/test_data.sql";


  public static void initTestSQl() {

    initTestSQl(null);
  }

  public static void initTestSQl(DataSource dataSource) {

    TableReader tableReader = dataSource != null ? new MysqlTableReader(dataSource) :
        new MysqlTableReader();

    if (!tableReader.exist("", TestHelper.TEST_TABLE_ROLE1)) {
      SqlExecutor sqlExecutor = tableReader.getSqlExecutor();
      List<String> sqlLines = SqlReader.readSqls(TEST_SQL);
      sqlExecutor.executeBatch(true, sqlLines);
    }
  }

  public static SingleRedisConfig getSingleRedisConfig() {
    SingleRedisConfig singleRedisConfig = new SingleRedisConfig();
    singleRedisConfig.setHost("127.0.0.1");
    singleRedisConfig.setPort(6379);
    singleRedisConfig.setPassword("123456");
    return singleRedisConfig;
  }

  public static ClusterRedisConfig getClusterRedisConfig() {
    ClusterRedisConfig clusterRedisConfig = new ClusterRedisConfig();
    clusterRedisConfig.addNode("127.0.0.1", 7361);
    clusterRedisConfig.addNode("127.0.0.1", 7362);
    clusterRedisConfig.addNode("127.0.0.1", 7363);
    clusterRedisConfig.addNode("127.0.0.1", 7364);
    clusterRedisConfig.addNode("127.0.0.1", 7365);
    clusterRedisConfig.addNode("127.0.0.1", 7366);
    clusterRedisConfig.setPassword("123456");
    return clusterRedisConfig;
  }

  public static RedisExecutor getRedisExecutor() {
    return new SingleRedisExecutor(getSingleRedisConfig());
  }

  public static RedisExecutor getJDKRedisExecutor() {
    SingleRedisConfig singleRedisConfig = getSingleRedisConfig();
    singleRedisConfig.setRedisCodec(new JdkRedisCodec());
    return new SingleRedisExecutor(singleRedisConfig);
  }

  public static DataSource mockDataSource() throws SQLException {
    return TestDataSourceHelper.mockDataSource();
  }
}