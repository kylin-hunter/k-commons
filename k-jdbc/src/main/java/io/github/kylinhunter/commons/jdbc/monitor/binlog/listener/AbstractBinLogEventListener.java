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

import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventHeader;
import com.github.shyiko.mysql.binlog.event.EventHeaderV4;
import com.github.shyiko.mysql.binlog.event.EventType;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.jdbc.meta.cache.DatabaseMetaCache;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.BasicEventProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.DeleteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.EventProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.FormatDescriptionEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.QueryEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.RotateEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.TableMapEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.UpdateRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event.WriteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.SavePoint;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.Map;
import javax.sql.DataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description EventListener
 * @date 2023-11-25 02:56
 */
@Slf4j
public abstract class AbstractBinLogEventListener<T extends Context> implements
    BinLogEventListener {

  @Setter
  private SavePointManager savePointManager;

  protected T context;

  private final Map<EventType, EventProcessor> processors = MapUtils.newHashMap();

  private DatabaseMetaCache databaseMetaCache;

  /**
   * @param dataSource dataSource
   * @title init
   * @description init
   * @author BiJi'an
   * @date 2023-12-10 01:34
   */
  public void init(DataSource dataSource) {
    this.databaseMetaCache = new DatabaseMetaCache(dataSource);
    addEventProcessor(EventType.ROTATE, new RotateEventDataProcessor());
    addEventProcessor(EventType.TABLE_MAP, new TableMapEventDataProcessor());
    addEventProcessor(EventType.EXT_WRITE_ROWS, new WriteRowsEventDataProcessor());
    addEventProcessor(EventType.EXT_DELETE_ROWS, new DeleteRowsEventDataProcessor());
    addEventProcessor(EventType.EXT_UPDATE_ROWS, new UpdateRowsEventDataProcessor());
    addEventProcessor(EventType.FORMAT_DESCRIPTION, new FormatDescriptionEventDataProcessor());
    addEventProcessor(EventType.QUERY, new QueryEventDataProcessor());

  }

  public void addEventProcessor(EventType eventType, BasicEventProcessor eventProcessor) {

    eventProcessor.setDatabaseMetaCache(databaseMetaCache);
    processors.put(eventType, eventProcessor);
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
      EventHeaderV4 headerV4 = (EventHeaderV4) header;
      long nextPosition = headerV4.getNextPosition();
      processEvent(headerV4, data);
      String binLogName = context.getBinLogFilename();
      if (!StringUtil.isEmpty(binLogName) && nextPosition >= 0) {
        savePointManager.save(new SavePoint(binLogName, nextPosition));
      } else {
        log.warn("invalid save point: {}/{}", binLogName, nextPosition);
      }
    } else {
      log.warn("Unsupported event={}", header);
    }
    log.info("@event process end@ header={}", header);
  }

  /**
   * @param eventHeaderV4 eventHeaderV4
   * @param data          data
   * @title process
   * @description process
   * @author BiJi'an
   * @date 2023-12-10 01:00
   */
  private void processEvent(EventHeaderV4 eventHeaderV4, EventData data) {
    EventType eventType = eventHeaderV4.getEventType();
    EventProcessor eventProcessor = processors.get(eventType);
    if (eventProcessor != null) {
      eventProcessor.process(this, data, this.context);
    } else {
      log.info("skip event={}", eventType);
    }
  }
}
