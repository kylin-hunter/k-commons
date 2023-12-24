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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.bean;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Config;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 21:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BinMonitorConfig extends Config {

  private List<BinTable> binTables = ListUtils.newArrayList();

  /**
   * @param binTable binTable
   * @title add
   * @description add
   * @author BiJi'an
   * @date 2023-12-17 17:17
   */
  public void add(BinTable binTable) {
    this.binTables.add(binTable);
  }
}
