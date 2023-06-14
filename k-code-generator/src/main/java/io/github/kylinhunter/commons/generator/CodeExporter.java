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
package io.github.kylinhunter.commons.generator;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.generator.context.bean.clazz.ClassInfo;
import io.github.kylinhunter.commons.generator.template.TemplateEngine;
import io.github.kylinhunter.commons.generator.template.TemplateExecutor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-10 01:29
 */
@C
@RequiredArgsConstructor
@Slf4j
public class CodeExporter {
  @CSet private TemplateEngine templateEngine;

  /**
   * @param templateContexts templateInfos
   * @return void
   * @title output
   * @description
   * @author BiJi'an
   * @date 2023-02-11 23:36
   */
  public void export(List<TemplateContext> templateContexts) {
    for (TemplateContext templateContext : templateContexts) {
      templateContext.getContext().forEach((k, v) -> log.info("{}:{}", k, v));
      export(templateContext);
    }
  }

  /**
   * @param templateContext templateInfo
   * @return void
   * @title output
   * @description
   * @author BiJi'an
   * @date 2023-02-11 23:36
   */
  private void export(TemplateContext templateContext) {
    TemplateExecutor templateExecutor =
        templateEngine.createTemplateExecutor(templateContext.getContext());
    Template template = templateContext.getTemplate();
    Module module = templateContext.getModuleInfo().getModule();
    TemplateStrategy strategy = template.getStrategy();
    log.info("export module=>{}/template={}", module.getName(), template.getName());
    templateExecutor
        .tmplate(template.getName(), strategy.getTemplateEncoding())
        .outputRelativePath(getOutputRelativePath(templateContext))
        .extension(strategy.getExtension())
        .encoding(strategy.getOutputEncoding())
        .build();
    templateExecutor.output();
  }

  /**
   * @param templateContext templateContext
   * @return java.lang.String
   * @title getOutputRelativePath
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:14
   */
  public String getOutputRelativePath(TemplateContext templateContext) {
    ClassInfo classInfo = templateContext.getClassInfo();
    return classInfo.getPackageName().replaceAll("\\.", "/") + "/" + classInfo.getName();
  }
}
