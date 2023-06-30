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

import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.io.file.FilenameUtils;
import io.github.kylinhunter.commons.lang.strings.StringConst;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 22:22
 */
@Getter
@Setter
public class TemplateInfo {
  private String name;
  private String encoding;

  public TemplateInfo(String name, String encoding, String defaultExtension) {
    ExceptionChecker.checkNotEmpty(name, "name not empty");
    String extension = FilenameUtils.getExtension(name);
    if (!StringUtil.isEmpty(defaultExtension) && !defaultExtension.equalsIgnoreCase(extension)) {
      name = name + StringConst.DOT + defaultExtension;
    }
    this.name = name;
    this.encoding = encoding;
  }
}
