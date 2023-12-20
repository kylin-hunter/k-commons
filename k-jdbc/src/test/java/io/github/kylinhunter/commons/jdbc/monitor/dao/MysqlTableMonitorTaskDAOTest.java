package io.github.kylinhunter.commons.jdbc.monitor.dao;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import io.github.kylinhunter.commons.jdbc.TestDataSourceHelper;
import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.TableMonitorTask;
import io.github.kylinhunter.commons.jdbc.monitor.dao.imp.MysqlTableMonitorTaskDAO;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import java.sql.SQLException;
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
    DataSource dataSource = TestDataSourceHelper.mockDataSource();
    MysqlTableMonitorTaskDAO scanProcessorDAO = new MysqlTableMonitorTaskDAO(dataSource);
    ReflectUtils.setField(scanProcessorDAO, "sqlExecutor", sqlExecutor);
    String tableName = TestHelper.MONITOR_SCAN_TASK;
    String bizTable = "bizTable_xxx";

    scanProcessorDAO.ensureDestinationExists(tableName);

    scanProcessorDAO.clean(tableName, bizTable);

    TableMonitorTask tableMonitorTask1 = scanProcessorDAO.findById(tableName, bizTable, "dataId-1");
    Assertions.assertNull(tableMonitorTask1);

    tableMonitorTask1 = new TableMonitorTask();
    tableMonitorTask1.setTableName(bizTable);
    tableMonitorTask1.setDataId("dataId-1");
    tableMonitorTask1.setOp(1);
    tableMonitorTask1.setStatus(2);
    scanProcessorDAO.save(tableName, tableMonitorTask1);

    System.out.println("scanProcessor1:" + tableMonitorTask1);

    Mockito.when(sqlExecutor.query(anyString(), Mockito.any(ResultSetHandler.class), eq(bizTable),
        eq("dataId-1"))).thenReturn(tableMonitorTask1);

    TableMonitorTask tableMonitorTask2 = scanProcessorDAO.findById(tableName, bizTable, "dataId-1");
    System.out.println("scanProcessor2:" + tableMonitorTask2);

    Assertions.assertNotNull(tableMonitorTask2);
    Assertions.assertEquals(tableMonitorTask1, tableMonitorTask2);

    TableMonitorTask tableMonitorTask2Update = new TableMonitorTask();
    tableMonitorTask2Update.setTableName(bizTable);
    tableMonitorTask2Update.setDataId("dataId-1");
    tableMonitorTask2Update.setOp(11);
    tableMonitorTask2Update.setStatus(21);

    scanProcessorDAO.update(tableName, tableMonitorTask2Update);

    Mockito.when(sqlExecutor.query(anyString(), Mockito.any(ResultSetHandler.class), eq(bizTable),
        eq("dataId-1"))).thenReturn(tableMonitorTask2Update);
    TableMonitorTask tableMonitorTask3 = scanProcessorDAO.findById(tableName, bizTable, "dataId-1");
    System.out.println("scanProcessor3:" + tableMonitorTask3);

    Assertions.assertNotNull(tableMonitorTask3);
    Assertions.assertNotEquals(tableMonitorTask1, tableMonitorTask3);

    Assertions.assertEquals(11, tableMonitorTask3.getOp());

    Assertions.assertEquals(21, tableMonitorTask3.getStatus());


  }
}