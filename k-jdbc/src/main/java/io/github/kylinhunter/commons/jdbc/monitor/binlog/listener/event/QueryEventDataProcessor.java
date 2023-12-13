package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.event;

import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.BinLogEventListener;
import io.github.kylinhunter.commons.jdbc.monitor.binlog.listener.Context;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 00:23
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
  public void process(BinLogEventListener binLogEventListener, EventData data,
      Context context) {
    if (data instanceof QueryEventData) {
      QueryEventData eventData = (QueryEventData) data;
      String database = eventData.getDatabase();
      int errorCode = eventData.getErrorCode();
      String sql = eventData.getSql();

      if (!StringUtil.isEmpty(sql) && sql.trim().contains("alter")) {
        String tableName = getAlterTableName(sql);
        if (!StringUtil.isEmpty(tableName)) {
          databaseMetaCache.updateTableCache(new TableId(database, tableName), true);
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


}