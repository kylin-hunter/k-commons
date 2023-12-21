package io.github.kylinhunter.commons.jdbc.binlog.dao.imp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.binlog.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MysqlSavePointDAOTest {

  @Test
  void test() throws SQLException {

    DataSource dataSource = TestHelper.mockDataSource();
    SqlExecutor sqlExecutor = Mockito.mock(SqlExecutor.class);
    MysqlSavePointDAO dao = new MysqlSavePointDAO(dataSource);
    ReflectUtils.setField(dao, "sqlExecutor", sqlExecutor);

    dao.save(new SavePoint());

    dao.update(new SavePoint());
    SavePoint savePoint = new SavePoint();
    savePoint.setPosition(999);
    when(sqlExecutor.query(anyString(), any(ResultSetHandler.class), any())).thenReturn(
        savePoint);

    SavePoint getResult = dao.get();
    Assertions.assertEquals(999, getResult.getPosition());
  }
}