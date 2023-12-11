package io.github.kylinhunter.commons.jdbc.monitor.dao;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.TableMonitorTask;
import io.github.kylinhunter.commons.jdbc.monitor.dao.imp.MysqlTableMonitorTaskDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MysqlTableMonitorTaskDAOTest {

  @Test
  void test() {
    MysqlTableMonitorTaskDAO scanProcessorDAO = new MysqlTableMonitorTaskDAO();
    String tableName = TestHelper.MONITOR_SCAN_TASK;
    String bizTable = "bizTable_xxx";

    scanProcessorDAO.ensureTableExists(tableName);

    scanProcessorDAO.clean(tableName, bizTable);

    TableMonitorTask tableMonitorTask1 = scanProcessorDAO.findById(tableName, bizTable, "dataId-1");
    Assertions.assertNull(tableMonitorTask1);

    tableMonitorTask1 = new TableMonitorTask();
    tableMonitorTask1.setId(bizTable);
    tableMonitorTask1.setDataId("dataId-1");
    tableMonitorTask1.setOp(1);
    tableMonitorTask1.setStatus(2);
    scanProcessorDAO.save(tableName, tableMonitorTask1);

    System.out.println("scanProcessor1:" + tableMonitorTask1);

    TableMonitorTask tableMonitorTask2 = scanProcessorDAO.findById(tableName, bizTable, "dataId-1");
    System.out.println("scanProcessor2:" + tableMonitorTask2);

    Assertions.assertNotNull(tableMonitorTask2);
    Assertions.assertEquals(tableMonitorTask1, tableMonitorTask2);

    tableMonitorTask2.setId(bizTable);
    tableMonitorTask2.setDataId("dataId-1");
    tableMonitorTask2.setOp(11);
    tableMonitorTask2.setStatus(21);

    scanProcessorDAO.update(tableName, tableMonitorTask2);

    TableMonitorTask tableMonitorTask3 = scanProcessorDAO.findById(tableName, bizTable, "dataId-1");
    System.out.println("scanProcessor3:" + tableMonitorTask3);

    Assertions.assertNotNull(tableMonitorTask3);
    Assertions.assertNotEquals(tableMonitorTask1, tableMonitorTask3);

    Assertions.assertEquals(11, tableMonitorTask3.getOp());

    Assertions.assertEquals(21, tableMonitorTask3.getStatus());


  }
}