/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventHeader;
import com.github.shyiko.mysql.binlog.event.EventHeaderV4;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.FormatDescriptionEventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.RotateEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.SavePoint;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.sql.DataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description EventListener
 * @date 2023-11-25 02:56
 */
@Slf4j
public abstract class AbstractBinLogEventListener implements BinLogEventListener {

  @Setter private SavePointManager savePointManager;
  protected DataSource dataSource;
  private String currentBinlogName;
  protected final Map<Long, TableId> tables = MapUtils.newHashMap();
  protected final Map<TableId, TableMeta> tableMetas = MapUtils.newHashMap();
  protected final Map<TableId, ColumnMetas> columnMetas = MapUtils.newHashMap();

  protected DatabaseMetaReader databaseMetaReader;
  private TableReader tableReader;
  private ColumnReader columnReader;
  private final Pattern PATTERN_ALTER_TABLE_NAME = Pattern.compile("alter table \\s*(\\S+)\\s*");

  /**
   * @param tableId tableId
   * @param tableName tableName
   * @param forceUpdate forceUpdate
   * @title addTable
   * @description addTable
   * @author BiJi'an
   * @date 2023-12-09 23:27
   */
  public void updateTableCache(
      Long tableId, String database, String tableName, boolean forceUpdate) {
    TableId tableKey = new TableId(database, tableName);
    tables.put(tableId, tableKey);
    updateTableCache(tableKey, forceUpdate);
  }

  /**
   * @param tableId tableKey
   * @param forceUpdate forceUpdate
   * @title updateTableMeta
   * @description updateTableMeta
   * @author BiJi'an
   * @date 2023-12-09 23:29
   */
  public void updateTableCache(TableId tableId, boolean forceUpdate) {

    TableMeta tableMeta = tableMetas.get(tableId);
    if (tableMeta == null || forceUpdate) {
      tableMeta =
          tableReader.getTableMetaData(this.dataSource, tableId.getDatabase(), tableId.getName());
      if (tableMeta != null) {
        tableMetas.put(tableId, tableMeta);
        log.info("############# updateTableMeta={}", tableMeta);
      }
    }

    ColumnMetas columnMetas = this.columnMetas.get(tableId);
    if (columnMetas == null || forceUpdate) {
      columnMetas =
          columnReader.getColumnMetaData(this.dataSource, tableId.getDatabase(), tableId.getName());
      if (columnMetas != null) {
        this.columnMetas.put(tableId, columnMetas);
        log.info("############# updateColumnMeta={}", columnMetas);
      }
    }
  }

  /**
   * @param event event
   * @title onEvent
   * @description onEvent
   * @author BiJi'an
   * @date 2023-12-10 01:00
   */
  @Override
  public void onEvent(Event event) {
    EventHeader header = event.getHeader();
    EventData data = event.getData();

    log.info("@event process start@ header={} , data={}", header, data);

    if (header instanceof EventHeaderV4) {

      EventHeaderV4 eventHeaderV4 = (EventHeaderV4) header;
      long nextPosition = eventHeaderV4.getNextPosition();
      processEvent(eventHeaderV4, data);
      if (!StringUtil.isEmpty(currentBinlogName) && nextPosition >= 0) {
        savePointManager.save(new SavePoint(currentBinlogName, nextPosition));
        log.info("save point : {}/{}", currentBinlogName, nextPosition);
      } else {
        log.warn("invalid save point: {}/{}", currentBinlogName, nextPosition);
      }
    } else {
      log.warn("Unsupported event={}", header);
    }

    log.info("@event process end@ header={}", header);
  }

  /**
   * @param eventHeaderV4 eventHeaderV4
   * @param data data
   * @title process
   * @description process
   * @author BiJi'an
   * @date 2023-12-10 01:00
   */
  private void processEvent(EventHeaderV4 eventHeaderV4, EventData data) {
    EventType eventType = eventHeaderV4.getEventType();

    switch (eventType) {
      case ROTATE:
        {
          processEventRotate(data);
          break;
        }
      case FORMAT_DESCRIPTION:
        {
          procssEventFormatDescription(data);
          break;
        }
      case TABLE_MAP:
        {
          processEventTableMap(data);
          break;
        }
      case EXT_WRITE_ROWS:
        {
          processEventExtWriteRows(data);
          break;
        }
      case EXT_DELETE_ROWS:
        {
          processEventExtDeleteRows(data);
          break;
        }
      case EXT_UPDATE_ROWS:
        {
          processEventExtUpdateRows(data);
          break;
        }
      case QUERY:
        {
          processEventQuery(data);
          break;
        }
      default:
        {
          log.info("skip event={}", eventType);
        }
    }
  }

  /**
   * @param sql sql
   * @return java.lang.String
   * @title getAlterTableName
   * @description getAlterTableName
   * @author BiJi'an
   * @date 2023-12-10 01:13
   */
  public String getAlterTableName(String sql) {
    Matcher matcher = PATTERN_ALTER_TABLE_NAME.matcher(sql);
    if (matcher.find()) {
      return matcher.group(1);
    }
    return "";
  }

  /**
   * @param data data
   * @title processEventQuery
   * @description processEventQuery
   * @author BiJi'an
   * @date 2023-12-10 01:07
   */
  private void processEventQuery(EventData data) {
    if (data instanceof QueryEventData) {
      QueryEventData eventData = (QueryEventData) data;
      String database = eventData.getDatabase();
      int errorCode = eventData.getErrorCode();
      String sql = eventData.getSql();

      if (!StringUtil.isEmpty(sql) && sql.trim().contains("alter")) {
        String tableName = getAlterTableName(sql);
        if (!StringUtil.isEmpty(tableName)) {
          updateTableCache(new TableId(database, tableName), true);
        }
      }
      log.info("errorCode={},database={},sql={}", errorCode, database, sql);
    }
  }

  /**
   * @param data data
   * @title processEventRotate
   * @description processEventRotate
   * @author BiJi'an
   * @date 2023-12-10 01:07
   */
  private void processEventRotate(EventData data) {
    if (data instanceof RotateEventData) {
      RotateEventData eventData = (RotateEventData) data;
      String binlogFilename = eventData.getBinlogFilename();
      long binlogPosition = eventData.getBinlogPosition();
      this.currentBinlogName = binlogFilename;
      log.info("binlog={},position={}", binlogFilename, binlogPosition);
    }
  }

  /**
   * @param data data
   * @title procssEventFormatDescription
   * @description procssEventFormatDescription
   * @author BiJi'an
   * @date 2023-12-10 01:08
   */
  private void procssEventFormatDescription(EventData data) {
    if (data instanceof FormatDescriptionEventData) {
      FormatDescriptionEventData eventData = (FormatDescriptionEventData) data;
      int binlogVersion = eventData.getBinlogVersion();
      String serverVersion = eventData.getServerVersion();
      log.info("binVersion={},serverVersion={}", binlogVersion, serverVersion);
    }
  }

  /**
   * @param data data
   * @title processEventTableMap
   * @description processEventTableMap
   * @author BiJi'an
   * @date 2023-12-10 01:08
   */
  private void processEventTableMap(EventData data) {
    if (data instanceof TableMapEventData) {
      TableMapEventData eventData = (TableMapEventData) data;
      String database = eventData.getDatabase();
      long tableId = eventData.getTableId();
      String table = eventData.getTable();
      log.info("table={}/{}/{}", database, tableId, table);
      updateTableCache(tableId, database, table, false);
    }
  }

  /**
   * @param data data
   * @title processEventExtWriteRows
   * @description processEventExtWriteRows
   * @author BiJi'an
   * @date 2023-12-10 01:08
   */
  private void processEventExtWriteRows(EventData data) {
    if (data instanceof WriteRowsEventData) {
      WriteRowsEventData eventData = (WriteRowsEventData) data;
      log.info("WriteRowsEventData={}", eventData);
      TableId tableId = tables.get(eventData.getTableId());
      insertDataRecord(tableId, eventData);
    }
  }

  /**
   * @param tableId tableKey
   * @param writeRowsEventData writeRowsEventData
   * @title insertEvent
   * @description insertEvent
   * @author BiJi'an
   * @date 2023-12-10 01:08
   */
  protected abstract void insertDataRecord(TableId tableId, WriteRowsEventData writeRowsEventData);

  private void processEventExtDeleteRows(EventData data) {
    if (data instanceof DeleteRowsEventData) {
      DeleteRowsEventData eventData = (DeleteRowsEventData) data;
      log.info("DeleteRowsEventData={}", eventData);
      TableId tableId = tables.get(eventData.getTableId());
      deleteDataRecord(tableId, eventData);
    }
  }

  /**
   * @param tableId tableKey
   * @param eventData eventData
   * @title deleteEvent
   * @description deleteEvent
   * @author BiJi'an
   * @date 2023-12-10 01:08
   */
  protected abstract void deleteDataRecord(TableId tableId, DeleteRowsEventData eventData);

  /**
   * @param data data
   * @title processEventExtUpdateRows
   * @description processEventExtUpdateRows
   * @author BiJi'an
   * @date 2023-12-10 01:08
   */
  private void processEventExtUpdateRows(EventData data) {
    if (data instanceof UpdateRowsEventData) {
      UpdateRowsEventData eventData = (UpdateRowsEventData) data;
      log.info("UpdateRowsEventData={}", eventData);
      TableId tableId = tables.get(eventData.getTableId());
      updateDataRecord(tableId, eventData);
    }
  }

  /**
   * @param tableId tableKey
   * @param eventData eventData
   * @title updateEvent
   * @description updateEvent
   * @author BiJi'an
   * @date 2023-12-10 01:08
   */
  protected abstract void updateDataRecord(TableId tableId, UpdateRowsEventData eventData);

  /**
   * @param dataSource dataSource
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-10 01:34
   */
  public void init(DataSource dataSource) {
    this.dataSource = dataSource;
    this.databaseMetaReader = new DatabaseMetaReader(dataSource);
    this.tableReader = this.databaseMetaReader.getTableReader();
    this.columnReader = this.databaseMetaReader.getColumnReader();
  }
}
