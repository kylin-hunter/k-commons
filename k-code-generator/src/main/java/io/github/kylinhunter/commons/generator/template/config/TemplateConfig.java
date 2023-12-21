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
package io.github.kylinhunter.commons.generator.template.config;

import io.github.kylinhunter.commons.generator.template.exception.TemplateException;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 01:40
 */
@Getter
@Setter
public class TemplateConfig {

  private Path templatePath = UserDirUtils.getDir(false, "templates_sample").toPath();
  private OutputConfig outputConfig = new OutputConfig();

  /**
   * @param templatePath templateDir
   * @title setOutputDir
   * @description
   * @author BiJi'an
   * @date 2023-01-08 23:08
   */
  public void setTemplatePath(Path templatePath) {
    if (Files.exists(templatePath)) {
      if (!Files.isDirectory(templatePath)) {
        throw new TemplateException("invalid templateDir" + templatePath);
      }
    } else {

      throw new TemplateException(" templateDir no exist " + templatePath);
    }
    this.templatePath = templatePath;
  }
}
