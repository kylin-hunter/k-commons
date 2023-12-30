package io.github.kylinhunter.commons.generator;

import io.github.kylinhunter.commons.generator.test.TestDataSourceHelper;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-17 10:46
 */
public class TestHelper {

  public static String MYSQL_JDBC_URL = "jdbc:mysql://localhost:3306/kp?useUnicode=true&characterEncoding=utf8&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
  public static String DATABASE = "kp";

  public static String TABLE_USER = "k_junit_code_user";
  public static String TABLE_ROLE = "k_junit_code_role";

  /**
   * @return javax.sql.DataSource
   * @title mockDataSource
   * @description mockDataSource
   * @author BiJi'an
   * @date 2023-12-17 15:57
   */
  public static DataSource mockDataSource() throws SQLException {
    return TestDataSourceHelper.mockDataSource();
  }

}