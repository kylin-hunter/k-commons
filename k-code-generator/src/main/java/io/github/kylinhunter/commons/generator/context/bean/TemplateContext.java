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
package io.github.kylinhunter.commons.generator.context.bean;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.context.bean.clazz.ClassInfo;
import io.github.kylinhunter.commons.generator.context.bean.module.ModuleInfo;
import java.util.Map;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 */
@Data
public class TemplateContext {
  private final ModuleInfo moduleInfo;
  private final Template template;
  private ClassInfo classInfo = new ClassInfo();
  private Map<String, Object> context = MapUtils.newHashMap();

  /**
   * @param contextData contextData
   * @return void
   * @title addAll
   * @description
   * @author BiJi'an
   * @date 2023-02-19 02:43
   */
  public void putContext(Map<String, ?> contextData) {
    if (contextData != null) {
      this.context.putAll(contextData);
    }
  }

  /**
   * @param key key
   * @param object object
   * @return void
   * @title put
   * @description
   * @author BiJi'an
   * @date 2023-02-19 22:17
   */
  public void putContext(String key, Object object) {
    this.context.put(key, object);
  }
}
