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
package io.github.kylinhunter.commons.jdbc.config.datasource.hikari;

import com.zaxxer.hikari.HikariConfig;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-18 11:01
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExHikariConfig extends HikariConfig {

  private int no;
  private String name;

  public ExHikariConfig(int no, String name) {
    this.no = no;
    if (!StringUtil.isEmpty(name)) {
      this.name = name;
    } else {
      this.name = "datasource-" + no;
    }
  }
}
