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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.EventData;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.BinLogEventListener;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 00:23
 */
@Slf4j
public class DeleteRowsEventDataProcessor extends BasicEventProcessor {

  /**
   * @param data data
   * @title processEventRotate
   * @description processEventRotate
   * @author BiJi'an
   * @date 2023-12-10 01:07
   */
  public void process(BinLogEventListener binLogEventListener, EventData data, Context context) {
    if (data instanceof DeleteRowsEventData) {
      DeleteRowsEventData eventData = (DeleteRowsEventData) data;
      log.info("DeleteRowsEventData={}", eventData);
      TableId tableId = databaseMetaCache.get(eventData.getTableId());
      deleteDataRecord(tableId, eventData, context);
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
  protected void deleteDataRecord(
      TableId tableId, DeleteRowsEventData eventData, Context context) {}
}
