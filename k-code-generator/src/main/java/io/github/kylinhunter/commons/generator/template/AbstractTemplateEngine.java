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
package io.github.kylinhunter.commons.generator.template;

import io.github.kylinhunter.commons.generator.template.config.ConfigCustomize;
import io.github.kylinhunter.commons.generator.template.config.TemplateConfig;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 21:56
 */
@Data
public abstract class AbstractTemplateEngine implements TemplateEngine {
  protected TemplateConfig templateConfig = new TemplateConfig();

  /**
   * @see TemplateEngine#customize
   */
  @Override
  public void customize(ConfigCustomize<TemplateConfig> configCustomize) {
    configCustomize.customize(this.templateConfig);
    this.reInit();
  }

  /**
   * @return void
   * @title initVelocityEngine
   * @description
   * @author BiJi'an
   * @date 2023-02-03 01:25
   */
  protected abstract void reInit();
}
