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
package io.github.kylinhunter.commons.jdbc.monitor.scan.bean;

import io.github.kylinhunter.commons.jdbc.monitor.bean.Table;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 21:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ScanTable extends Table {

  @Setter private TableScanConfig config;

  private String tableTimeName;

  private LocalDateTime initScanTime = LocalDateTime.now().minus(10, ChronoUnit.YEARS);

  private String initScanId = StringUtil.EMPTY;

  private long scanLimit = 3000;

  private int scanInterval = 1000;
}
