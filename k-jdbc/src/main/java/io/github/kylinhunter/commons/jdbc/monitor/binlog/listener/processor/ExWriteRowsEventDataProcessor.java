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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.processor;

import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.jdbc.binlog.listener.event.WriteRowsEventDataProcessor;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.bean.BinTable;
import io.github.kylinhunter.commons.jdbc.monitor.manager.TableTaskManager;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import java.io.Serializable;
import java.util.List;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-16 00:48
 */
@RequiredArgsConstructor
public class ExWriteRowsEventDataProcessor extends WriteRowsEventDataProcessor {

  private final TableTaskManager taskManager;
  private final TableProcessor tableProcessor;

  @Override
  protected void insertDataRecord(WriteRowsEventData eventData, Context context) {

    this.tableProcessor.process(eventData.getTableId(), eventData, this::process);
  }

  /**
   * @param binTable     monitorTable
   * @param eventData    eventData
   * @param pkColumnMeta pkColumnMeta
   * @title process
   * @description process
   * @author BiJi'an
   * @date 2023-12-23 02:19
   */
  private void process(BinTable binTable, WriteRowsEventData eventData, ColumnMeta pkColumnMeta) {

    List<Serializable[]> rows = eventData.getRows();
    for (Serializable[] row : rows) {
      if (pkColumnMeta.getPos() < row.length) {
        taskManager.save(binTable, String.valueOf(row[pkColumnMeta.getPos()]), RowOP.INSERT);
      }
    }
  }
}
