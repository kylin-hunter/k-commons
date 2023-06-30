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
package io.github.kylinhunter.commons.generator.configuration;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CC;
import io.github.kylinhunter.commons.generator.config.ConfigReader;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.generator.config.bean.Output;
import io.github.kylinhunter.commons.generator.template.TemplateEngine;
import io.github.kylinhunter.commons.generator.template.config.OutputConfig;
import io.github.kylinhunter.commons.generator.template.velocity.VelocityTemplateEngine;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.ResourceHelper.PathType;
import java.io.File;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-04 20:27
 */
@CC
public class CompConfiguration {

  /**
   * @return io.github.kylinhunter.commons.generator.config.bean.Config
   * @title config
   * @description
   * @author BiJi'an
   * @date 2023-02-16 23:51
   */
  @C
  public Config config() {
    return new ConfigReader().load();
  }

  /**
   * @param config config
   * @return io.github.kylinhunter.commons.template.TemplateExecutor
   * @title templateExecutor
   * @description
   * @author BiJi'an
   * @date 2023-02-16 23:51
   */
  @C
  public TemplateEngine templateEngine(Config config) {
    TemplateEngine templateEngine = new VelocityTemplateEngine();
    templateEngine.customize(
        templateConfig -> {
          Global global = config.getGlobal();

          String templatePath = global.getTemplatePath();
          File dirTemplate = ResourceHelper.getDir(templatePath, PathType.FILESYSTEM, true);
          templateConfig.setTemplatePath(dirTemplate.toPath());

          Output output = global.getOutput();
          String outputPath = output.getPath();
          OutputConfig outputConfig = templateConfig.getOutputConfig();
          outputConfig.setAutoCreate(output.isAutoCreate());
          outputConfig.setAutoClean(output.isAutoClean());
          outputConfig.setOutputPath(outputPath);
        });
    return templateEngine;
  }
}
