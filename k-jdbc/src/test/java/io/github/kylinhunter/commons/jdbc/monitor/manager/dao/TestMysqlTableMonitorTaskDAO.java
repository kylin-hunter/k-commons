package io.github.kylinhunter.commons.jdbc.monitor.manager.dao;

import io.github.kylinhunter.commons.jdbc.TestHelper;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowStatus;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.imp.MysqlTableMonitorTaskDAO;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;

public class TestMysqlTableMonitorTaskDAO {

  static String destination = TestHelper.TEST_SCAN_TASK2;
  static String tableName = "table";
  static String datbase = "database";


  public static void main(String[] args) {

    MysqlTableMonitorTaskDAO monitorTaskDAO = new MysqlTableMonitorTaskDAO();

    monitorTaskDAO.ensureDestinationExists(destination);

    monitorTaskDAO.clean(destination, datbase, tableName);

    TableMonitorTask tableMonitorTaskOld = monitorTaskDAO.findById(destination, datbase, tableName,
        "1");
    Assertions.assertNull(tableMonitorTaskOld);

    TableMonitorTask tableMonitorTask1 = new TableMonitorTask();
    tableMonitorTask1.setDb(datbase);
    tableMonitorTask1.setTableName(tableName);
    tableMonitorTask1.setDataId("1");
    tableMonitorTask1.setOp(RowOP.INSERT.getCode());
    tableMonitorTask1.setStatus(RowStatus.ERROR.getCode());
    monitorTaskDAO.save(destination, tableMonitorTask1);
    TableMonitorTask tableMonitorTask2 = new TableMonitorTask();
    tableMonitorTask2.setDb(datbase);
    tableMonitorTask2.setTableName(tableName);
    tableMonitorTask2.setDataId("2");
    tableMonitorTask2.setOp(RowOP.INSERT.getCode());
    tableMonitorTask2.setStatus(RowStatus.WAIT.getCode());
    monitorTaskDAO.save(destination, tableMonitorTask2);

    System.out.println("tableMonitorTask1:" + tableMonitorTask1);
    System.out.println("tableMonitorTask2:" + tableMonitorTask2);

    TableMonitorTask tableMonitorTask1_Old = monitorTaskDAO.findById(destination, datbase,
        tableName, "1");
    Assertions.assertNotNull(tableMonitorTask1_Old);
    Assertions.assertEquals(tableMonitorTask1, tableMonitorTask1_Old);

    tableMonitorTask1_Old.setOp(RowOP.UPDATE.getCode());
    tableMonitorTask1_Old.setStatus(RowStatus.WAIT.getCode());

    monitorTaskDAO.update(destination, tableMonitorTask1_Old);

    TableMonitorTask tableMonitorTask11Old2 = monitorTaskDAO.findById(destination, datbase,
        tableName, "1");
    System.out.println("tableMonitorTask11Old2:" + tableMonitorTask11Old2);

    Assertions.assertNotNull(tableMonitorTask11Old2);
    Assertions.assertNotEquals(tableMonitorTask1, tableMonitorTask11Old2);

    Assertions.assertEquals(RowOP.UPDATE.getCode(), tableMonitorTask11Old2.getOp());

    Assertions.assertEquals(RowStatus.WAIT.getCode(), tableMonitorTask11Old2.getStatus());

    testStatus(monitorTaskDAO);
  }

  static void testStatus(MysqlTableMonitorTaskDAO monitorTaskDAO) {
    int maxRetryCount = 3;

    List<TableMonitorTask> waitDatas = monitorTaskDAO.findWaitDatas(destination,
        LocalDateTime.now(), 1, 10);

    Assertions.assertEquals(2, waitDatas.size());
    TableMonitorTask tableMonitorTask = waitDatas.get(0);

    String dataId = tableMonitorTask.getDataId();

    monitorTaskDAO.updateStatusByStatus(destination, datbase, tableName,
        dataId, RowStatus.PROCESSING, RowStatus.WAIT);

    tableMonitorTask = monitorTaskDAO.findById(destination, datbase, tableName, dataId);

    Assertions.assertEquals(RowStatus.PROCESSING.getCode(), tableMonitorTask.getStatus());

    waitDatas = monitorTaskDAO.findWaitDatas(destination, LocalDateTime.now(), 1, 10);

    Assertions.assertEquals(1, waitDatas.size());

    // update to retring 1
    monitorTaskDAO.updateStatusByStatus(destination, datbase, tableName,
        dataId, RowStatus.RETRYING, RowStatus.PROCESSING);

    tableMonitorTask = monitorTaskDAO.findById(destination, datbase, tableName, dataId);

    Assertions.assertEquals(RowStatus.RETRYING.getCode(), tableMonitorTask.getStatus());

    monitorTaskDAO.batchRetry(destination, RowStatus.RETRYING, LocalDateTime.now());

    tableMonitorTask = monitorTaskDAO.findById(destination, datbase, tableName, dataId);

    Assertions.assertEquals(1, tableMonitorTask.getRetryTimes());

    // update to retring 2
    monitorTaskDAO.updateStatusByStatus(destination, datbase, tableName,
        dataId, RowStatus.RETRYING, RowStatus.WAIT);
    monitorTaskDAO.batchRetry(destination, RowStatus.RETRYING, LocalDateTime.now());
    tableMonitorTask = monitorTaskDAO.findById(destination, datbase, tableName, dataId);

    Assertions.assertEquals(2, tableMonitorTask.getRetryTimes());

    // update to retring 3
    monitorTaskDAO.updateStatusByStatus(destination, datbase, tableName,
        dataId, RowStatus.RETRYING, RowStatus.WAIT);
    monitorTaskDAO.batchRetry(destination, RowStatus.RETRYING, LocalDateTime.now());
    tableMonitorTask = monitorTaskDAO.findById(destination, datbase, tableName, dataId);
    Assertions.assertEquals(3, tableMonitorTask.getRetryTimes());

    // update to retring 4
    monitorTaskDAO.updateStatusByStatus(destination, datbase, tableName,
        dataId, RowStatus.RETRYING, RowStatus.WAIT);
    monitorTaskDAO.batchRetry(destination, RowStatus.RETRYING, LocalDateTime.now());
    tableMonitorTask = monitorTaskDAO.findById(destination, datbase, tableName, dataId);
    Assertions.assertEquals(4, tableMonitorTask.getRetryTimes());

    // update to retring 5
    monitorTaskDAO.updateStatusByStatus(destination, datbase, tableName,
        dataId, RowStatus.RETRYING, RowStatus.WAIT);
    monitorTaskDAO.batchRetry(destination, RowStatus.RETRYING, LocalDateTime.now());
    tableMonitorTask = monitorTaskDAO.findById(destination, datbase, tableName, dataId);
    Assertions.assertEquals(4, tableMonitorTask.getRetryTimes());

    // batch error
    monitorTaskDAO.batchError(destination, maxRetryCount, LocalDateTime.now());

    tableMonitorTask = monitorTaskDAO.findById(destination, datbase, tableName, dataId);

    Assertions.assertEquals(4, tableMonitorTask.getRetryTimes());
    Assertions.assertEquals(RowStatus.ERROR.getCode(), tableMonitorTask.getStatus());


  }
}