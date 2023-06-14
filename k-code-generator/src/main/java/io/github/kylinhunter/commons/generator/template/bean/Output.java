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
package io.github.kylinhunter.commons.generator.template.bean;

import java.nio.charset.Charset;
import java.nio.file.Path;
import lombok.Data;

/**
 * @author BiJi'an
 * @description the templateInfo output message
 * @date 2023-01-06 00:50
 */
@Data
public class Output {
  private TemplateInfo templateInfo;
  private Charset encoding;
  private String extension;
  private Path outputPath;

  /**
   * @param templateInfo templateInfo
   * @return
   * @title Output
   * @description
   * @author BiJi'an
   * @date 2023-01-08 22:55
   */
  public Output(TemplateInfo templateInfo) {
    this.templateInfo = templateInfo;
  }
}
