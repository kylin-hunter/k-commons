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
package io.github.kylinhunter.commons.jdbc.monitor.scan;

import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 21:22
 */
@Data
@Builder
public class ScanTable {

  @Setter private TableScanConfig config;

  private String database;
  private String tableName;
  private String tablePkName;
  private String tableTimeName;
  @Default private String destination = "k_table_monitor_scan_task";
  @Default private LocalDateTime initScanTime = LocalDateTime.now().minus(10, ChronoUnit.YEARS);
  @Default private String initScanId = StringUtil.EMPTY;

  @Default private long scanLimit = 3000;
  @Default private int scanInterval = 1000;

  public String getServerId() {
    return this.config.getServerId();
  }
}
