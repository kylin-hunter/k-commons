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
package io.github.kylinhunter.commons.jdbc.binlog;

import com.github.shyiko.mysql.binlog.BinaryLogClient.EventListener;
import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventHeader;
import com.github.shyiko.mysql.binlog.event.EventHeaderV4;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.FormatDescriptionEventData;
import com.github.shyiko.mysql.binlog.event.RotateEventData;
import com.github.shyiko.mysql.binlog.event.TableMapEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.DefaultSavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.bean.SavePoint;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description EventListener
 * @date 2023-11-25 02:56
 */
@Slf4j
public class DefaultBinLogEventListener implements EventListener {

  private final SavePointManager savePointManager = new DefaultSavePointManager();
  private String binlogName;

  @Override
  public void onEvent(Event event) {
    EventHeader header = event.getHeader();
    EventData data = event.getData();
    //    log.info("header={} \r\n data={}", header, data);

    if (header instanceof EventHeaderV4) {
      EventHeaderV4 eventHeaderV4 = (EventHeaderV4) header;
      EventType eventType = eventHeaderV4.getEventType();
      long nextPosition = eventHeaderV4.getNextPosition();
      process(eventType, data);
      if (!StringUtil.isEmpty(binlogName) && nextPosition > 0) {
        savePointManager.saveOrUpdate(new SavePoint(binlogName, nextPosition));
        log.info("save point : event={},nextPosition={}", eventType, nextPosition);
      }
    }
  }

  private void process(EventType eventType, EventData data) {
    switch (eventType) {
      case ROTATE:
        {
          eventROTATE(data);
          break;
        }
      case FORMAT_DESCRIPTION:
        {
          eventFORMAT_DESCRIPTION(data);
          break;
        }
      case TABLE_MAP:
        {
          eventTABLE_MAP(data);
          break;
        }
      case EXT_WRITE_ROWS:
        {
          eventEXT_WRITE_ROWS(data);
        }
      case EXT_DELETE_ROWS:
        {
          eventEXT_DELETE_ROWS(data);
        }
      case EXT_UPDATE_ROWS:
        {
          eventEXT_UPDATE_ROWS(data);
        }
    }
  }

  private void eventROTATE(EventData data) {
    if (data instanceof RotateEventData) {
      RotateEventData eventData = (RotateEventData) data;
      String binlogFilename = eventData.getBinlogFilename();
      long binlogPosition = eventData.getBinlogPosition();
      this.binlogName = binlogFilename;
      log.info("binlogFilename={},binlogPosition={}", binlogFilename, binlogPosition);
    }
  }

  private void eventFORMAT_DESCRIPTION(EventData data) {
    if (data instanceof FormatDescriptionEventData) {
      FormatDescriptionEventData eventData = (FormatDescriptionEventData) data;
      int binlogVersion = eventData.getBinlogVersion();
      String serverVersion = eventData.getServerVersion();
      log.info("binVersion={},serverVersion={}", binlogVersion, serverVersion);
    }
  }

  private void eventTABLE_MAP(EventData data) {
    if (data instanceof TableMapEventData) {
      TableMapEventData eventData = (TableMapEventData) data;
      log.info(
          "table={}/{}/{}", eventData.getDatabase(), eventData.getTableId(), eventData.getTable());
    }
  }

  private void eventEXT_WRITE_ROWS(EventData data) {
    if (data instanceof WriteRowsEventData) {
      WriteRowsEventData eventData = (WriteRowsEventData) data;
      log.info("writeRowsEventData={}", eventData);
    }
  }

  private void eventEXT_DELETE_ROWS(EventData data) {
    if (data instanceof DeleteRowsEventData) {
      DeleteRowsEventData eventData = (DeleteRowsEventData) data;
      log.info("DeleteRowsEventData={}", eventData);
    }
  }

  private void eventEXT_UPDATE_ROWS(EventData data) {
    if (data instanceof UpdateRowsEventData) {
      UpdateRowsEventData eventData = (UpdateRowsEventData) data;
      log.info("UpdateRowsEventData={}", eventData);
    }
  }
}
