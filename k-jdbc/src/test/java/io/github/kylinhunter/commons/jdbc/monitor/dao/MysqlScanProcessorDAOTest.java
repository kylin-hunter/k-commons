package io.github.kylinhunter.commons.jdbc.monitor.dao;

import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.imp.MysqlScanProcessorDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MysqlScanProcessorDAOTest {

  @Test
  void test() {
    MysqlScanProcessorDAO scanProcessorDAO = new MysqlScanProcessorDAO(true);
    String tableName = "k_junit_table_monitor_scan_task";
    String bizTable = "bizTable_xxx";

    scanProcessorDAO.ensureTableExists(tableName);

    scanProcessorDAO.clean(tableName, bizTable);

    ScanProcessor scanProcessor1 = scanProcessorDAO.findById(tableName, bizTable, "dataId-1");
    Assertions.assertNull(scanProcessor1);

    scanProcessor1 = new ScanProcessor();
    scanProcessor1.setId(bizTable);
    scanProcessor1.setDataId("dataId-1");
    scanProcessor1.setOp(1);
    scanProcessor1.setStatus(2);
    scanProcessorDAO.save(tableName, scanProcessor1);

    System.out.println("scanProcessor1:" + scanProcessor1);

    ScanProcessor scanProcessor2 = scanProcessorDAO.findById(tableName, bizTable, "dataId-1");
    System.out.println("scanProcessor2:" + scanProcessor2);

    Assertions.assertNotNull(scanProcessor2);
    Assertions.assertEquals(scanProcessor1, scanProcessor2);

    scanProcessor2.setId(bizTable);
    scanProcessor2.setDataId("dataId-1");
    scanProcessor2.setOp(11);
    scanProcessor2.setStatus(21);

    scanProcessorDAO.update(tableName, scanProcessor2);

    ScanProcessor scanProcessor3 = scanProcessorDAO.findById(tableName, bizTable, "dataId-1");
    System.out.println("scanProcessor3:" + scanProcessor3);

    Assertions.assertNotNull(scanProcessor3);
    Assertions.assertNotEquals(scanProcessor1, scanProcessor3);

    Assertions.assertEquals(11, scanProcessor3.getOp());

    Assertions.assertEquals(21, scanProcessor3.getStatus());


  }
}