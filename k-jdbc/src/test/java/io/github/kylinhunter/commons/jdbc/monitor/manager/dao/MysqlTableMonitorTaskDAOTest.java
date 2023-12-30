package io.github.kylinhunter.commons.jdbc.monitor.manager.dao;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowStatus;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.imp.MysqlTableMonitorTaskDAO;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.ResultSetHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class MysqlTableMonitorTaskDAOTest {

  @SuppressWarnings("unchecked")
  @Test
  void test() throws SQLException {
    SqlExecutor sqlExecutor = Mockito.mock(SqlExecutor.class);
    DataSource dataSource = TestHelper.mockDataSource();
    MysqlTableMonitorTaskDAO scanProcessorDAO = new MysqlTableMonitorTaskDAO(dataSource);
    ReflectUtils.setField(scanProcessorDAO, "sqlExecutor", sqlExecutor);
    String destination = TestHelper.TEST_SCAN_TASK;
    String database = "";
    String table = TestHelper.TEST_TABLE_ROLE1;

    scanProcessorDAO.ensureDestinationExists(destination);

    scanProcessorDAO.clean(destination, database, table);

    TableMonitorTask tableMonitorTask1 = scanProcessorDAO.findById(destination, database, table,
        "1");
    Assertions.assertNull(tableMonitorTask1);

    tableMonitorTask1 = new TableMonitorTask();
    tableMonitorTask1.setTableName(table);
    tableMonitorTask1.setDataId("1");
    tableMonitorTask1.setOp(1);
    tableMonitorTask1.setStatus(2);
    scanProcessorDAO.save(destination, tableMonitorTask1);

    System.out.println("scanProcessor1:" + tableMonitorTask1);

    Mockito.when(sqlExecutor.query(anyString(), any(ResultSetHandler.class),
        anyString(), eq(table),
        eq("1"))).thenReturn(tableMonitorTask1);

    TableMonitorTask tableMonitorTask2 = scanProcessorDAO.findById(destination, database, table,
        "1");
    System.out.println("scanProcessor2:" + tableMonitorTask2);

    Assertions.assertNotNull(tableMonitorTask2);
    Assertions.assertEquals(tableMonitorTask1, tableMonitorTask2);

    TableMonitorTask tableMonitorTask2Update = new TableMonitorTask();
    tableMonitorTask2Update.setTableName(table);
    tableMonitorTask2Update.setDataId("1");
    tableMonitorTask2Update.setOp(11);
    tableMonitorTask2Update.setStatus(21);

    scanProcessorDAO.update(destination, tableMonitorTask2Update);

    Mockito.when(sqlExecutor.query(anyString(), any(ResultSetHandler.class),
        anyString(), eq(table),
        eq("1"))).thenReturn(tableMonitorTask2Update);
    TableMonitorTask tableMonitorTask3 = scanProcessorDAO.findById(destination, database, table,
        "1");
    System.out.println("scanProcessor3:" + tableMonitorTask3);

    Assertions.assertNotNull(tableMonitorTask3);
    Assertions.assertNotEquals(tableMonitorTask1, tableMonitorTask3);

    Assertions.assertEquals(11, tableMonitorTask3.getOp());

    Assertions.assertEquals(21, tableMonitorTask3.getStatus());

    Mockito.when(sqlExecutor.query(anyString(), any(ResultSetHandler.class),
        anyInt(), any(LocalDateTime.class),
        anyInt())).thenReturn(ListUtils.newArrayList(new TableMonitorTask()));

    List<TableMonitorTask> waitDatas = scanProcessorDAO.findWaitDatas(destination,
        LocalDateTime.now(), 1, 10);
    Assertions.assertEquals(1, waitDatas.size());

    Mockito.when(sqlExecutor.execute(anyString(),
        anyInt(), anyString(), anyString(), anyString(),
        anyInt())).thenReturn(11);

    int count = scanProcessorDAO.updateStatusByStatus(destination, database, table, "id",
        RowStatus.PROCESSING,
        RowStatus.WAIT);
    Assertions.assertEquals(11, count);


  }
}