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
package io.github.kylinhunter.commons.generator.config.bean;

import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.Map;
import lombok.Data;

@Data
public class Template {
  private boolean enabled = true;
  private String name;
  protected Map<String, Object> context = MapUtils.newHashMap();
  private TemplateStrategy strategy;

  /**
   * @param context context
   * @return void
   * @title putContext
   * @description
   * @author BiJi'an
   * @date 2023-02-19 23:15
   */
  public void putContext(Map<String, Object> context) {
    if (context != null) {
      this.context.putAll(context);
    }
  }

  /**
   * @param templates templateConfigs
   * @return void
   * @title merge
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:12
   */
  public void merge(Templates templates) {
    if (strategy == null) {
      this.strategy = new TemplateStrategy();
    }
    this.strategy.merge(templates.getStrategy());
    this.putContext(templates.getContext());
  }
}
