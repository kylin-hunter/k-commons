package io.github.kylinhunter.commons.jdbc.monitor.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-24 01:50
 */
@Data
public class Table {

  private String database;
  private String tableName;
  private String pkColName; // primary key
  private String destination = "k_table_monitor_task";

}