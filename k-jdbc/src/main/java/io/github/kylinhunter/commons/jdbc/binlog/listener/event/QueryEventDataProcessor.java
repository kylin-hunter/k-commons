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
package io.github.kylinhunter.commons.jdbc.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.EventType;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import io.github.kylinhunter.commons.jdbc.binlog.listener.BinLogEventListener;
import io.github.kylinhunter.commons.jdbc.binlog.listener.Context;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-16 00:23
 */
@Slf4j
public class QueryEventDataProcessor extends BasicEventProcessor {

  private final Pattern PATTERN_ALTER_TABLE_NAME = Pattern.compile("alter table \\s*(\\S+)\\s*");

  /**
   * @param data data
   * @title processEventRotate
   * @description processEventRotate
   * @author BiJi'an
   * @date 2023-12-10 01:07
   */
  public void process(BinLogEventListener binLogEventListener, EventData data, Context context) {
    if (data instanceof QueryEventData) {
      QueryEventData eventData = (QueryEventData) data;
      String database = eventData.getDatabase();
      int errorCode = eventData.getErrorCode();
      String sql = eventData.getSql();

      if (!StringUtil.isEmpty(sql)) {
        sql = sql.toLowerCase();
        if (sql.contains("alter")) {
          String tableName = getAlterTableName(sql);
          if (!StringUtil.isEmpty(tableName)) {
            tableIdManager.clean(database, tableName);
          }
        }
      }
      log.info("errorCode={},database={},sql={}", errorCode, database, sql);
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

  @Override
  public EventType getEventType() {
    return EventType.QUERY;
  }
}
