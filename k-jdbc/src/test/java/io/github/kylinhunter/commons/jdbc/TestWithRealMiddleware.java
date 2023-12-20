package io.github.kylinhunter.commons.jdbc;

import io.github.kylinhunter.commons.jdbc.binlog.redis.TestRedisExecutor;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.TestMysqlSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp.TestRedisSavePointManager;
import io.github.kylinhunter.commons.jdbc.dao.TestAbsctractBasicDAO;
import io.github.kylinhunter.commons.jdbc.execute.TestSqlExecutor;
import io.github.kylinhunter.commons.jdbc.meta.TestDatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.cache.TestTableIdManager;
import io.github.kylinhunter.commons.jdbc.meta.column.TestColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TestTableReader;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.TestTableMonitorListener;
import io.github.kylinhunter.commons.jdbc.monitor.dao.TestMysqlTableMonitorTaskDAO;
import java.sql.SQLException;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-17 19:37
 */
public class TestWithRealMiddleware {

  public static void main(String[] args) throws SQLException {
    TestRedisExecutor.main(null);
    TestTableReader.main(null);
    TestColumnReader.main(null);
    TestRedisSavePointManager.main(null);
    TestSqlExecutor.main(null);
    TestMysqlSavePointManager.main(null);
    TestDatabaseMetaReader.main(null);
    TestMysqlTableMonitorTaskDAO.main(null);
    TestAbsctractBasicDAO.main(null);
    TestTableIdManager.main(null);
    TestTableMonitorListener.main(null);

  }

}