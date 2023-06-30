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
package io.github.kylinhunter.commons.generator.config;

import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Modules;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.config.bean.Templates;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.name.NameRule;
import io.github.kylinhunter.commons.utils.yaml.YamlHelper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 16:12
 */
@Getter
@Slf4j
public class ConfigReader {

  private static final String DEFAULT_PATH = "k-generator-config.yaml";

  public Config load() {
    return this.load(null);
  }

  /**
   * @param path path
   * @return io.github.kylinhunter.commons.generator.config.bean.Config
   * @title load
   * @description
   * @author BiJi'an
   * @date 2023-02-12 22:23
   */
  public Config load(String path) {
    path = StringUtil.defaultString(path, DEFAULT_PATH);
    Config config = YamlHelper.loadFromPath(Config.class, path, NameRule.CAMEL_LOW);
    return afterLoad(config);
  }

  /**
   * @param config config
   * @return void
   * @title afterLoad
   * @description
   * @author BiJi'an
   * @date 2023-02-12 22:23
   */
  private Config afterLoad(Config config) {

    Modules modules = config.getModules();
    for (Module module : modules.getList()) {
      module.merge(modules);
    }

    Templates templates = config.getTemplates();
    for (Template template : templates.getList()) {
      template.merge(templates);
    }
    return config;
  }
}
