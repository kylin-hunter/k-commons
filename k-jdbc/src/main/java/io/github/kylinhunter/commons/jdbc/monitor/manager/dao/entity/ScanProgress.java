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
package io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-03 19:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScanProgress implements Serializable {

  private String db;
  private String tableName;
  private String saveDestination;
  private LocalDateTime nextScanTime;
  private String lastScanId;

  public ScanProgress(String db, String tableName, LocalDateTime nextScanTime, String lastScanId) {
    this.db = db;
    this.tableName = tableName;
    this.nextScanTime = nextScanTime;
    this.lastScanId = lastScanId;
  }
}
