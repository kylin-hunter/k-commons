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

import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.io.file.path.PathUtil;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
public class OutputConfig {

  private Path outputPath = UserDirUtils.getDir(true, "output").toPath();
  private boolean autoClean = false;
  private boolean autoCreate = true;
  private boolean override = true;
  private Charset defaultEncoding = StandardCharsets.UTF_8;

  /**
   * @param outputPath outputDir
   * @return void
   * @title setOutputDir
   * @description
   * @author BiJi'an
   * @date 2023-01-08 23:08
   */
  public void setOutputPath(Path outputPath) {

    PathUtil.checkDir(outputPath, autoCreate);
    this.outputPath = outputPath;
  }

  /**
   * @param outputPath outputPath
   * @return void
   * @title setOutputPath
   * @description
   * @author BiJi'an
   * @date 2023-02-26 15:20
   */
  public void setOutputPath(String outputPath) {
    File dir = ResourceHelper.getDir(outputPath, ResourceHelper.PathType.FILESYSTEM, true);
    this.setOutputPath(dir.toPath());
  }
}
