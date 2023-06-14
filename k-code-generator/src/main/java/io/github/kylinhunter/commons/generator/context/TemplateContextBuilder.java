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
package io.github.kylinhunter.commons.generator.context;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Database;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Modules;
import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.constant.ContextConsts;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.generator.context.bean.clazz.ClassInfo;
import io.github.kylinhunter.commons.generator.context.bean.clazz.FieldInfo;
import io.github.kylinhunter.commons.generator.context.bean.module.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.module.TableInfo;
import io.github.kylinhunter.commons.generator.function.ExpressionExecutor;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:24
 */
@C
@RequiredArgsConstructor
@Slf4j
public class TemplateContextBuilder {

  @CSet private Config config;
  @CSet private ModuleInfoReader moduleInfoReader;
  @CSet private ExpressionExecutor expressionExecutor;
  @CSet private FieldInfoConvertor fieldInfoConvertor;

  public List<TemplateContext> calculateContext() {
    List<TemplateContext> templateContexts = ListUtils.newArrayList();
    Modules modules = config.getModules();
    Database database = modules.getDatabase();
    for (Module module : modules.getList()) {
      ModuleInfo moduleInfo = moduleInfoReader.read(database, module);
      for (Template template : config.getTemplates().getList()) {
        if (template.isEnabled()) {
          TemplateContext templateContext = new TemplateContext(moduleInfo, template);
          calculateContext(templateContext);
          templateContexts.add(templateContext);
        }
      }
    }

    return templateContexts;
  }

  /**
   * @param templateContext templateContext
   * @return io.github.kylinhunter.commons.generator.context.bean.TemplateContext
   * @title toTemplateContext
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:23
   */
  private void calculateContext(TemplateContext templateContext) {
    templateContext.putContext(ContextConsts.CLASS, templateContext.getClassInfo());
    templateContext.putContext(ContextConsts.MODULE, templateContext.getModuleInfo());
    templateContext.putContext(templateContext.getTemplate().getContext());
    templateContext.putContext(templateContext.getModuleInfo().getModule().getContext());
    processPackageName(templateContext);
    processClassName(templateContext);
    processSuperClass(templateContext);
    processInterfaces(templateContext);
    processFields(templateContext);
    processComment(templateContext);
  }

  /**
   * @param templateContext templateContext
   * @return void
   * @title processFields
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:20
   */
  private void processFields(TemplateContext templateContext) {
    ClassInfo classInfo = templateContext.getClassInfo();
    ModuleInfo moduleInfo = templateContext.getModuleInfo();
    Database database = moduleInfo.getDatabase();
    TableInfo tableInfo = moduleInfo.getTableInfo();
    Table table = tableInfo.getTable();
    tableInfo
        .getColumnMetas()
        .forEach(
            columnMeta -> {
              FieldInfo fieldInfo = fieldInfoConvertor.convert(columnMeta, table, database);
              classInfo.addImport(fieldInfo.getFullType());
              classInfo.getFields().add(fieldInfo);
            });
  }

  /**
   * @param templateContext templateContext
   * @return void
   * @title processPackageName
   * @description
   * @author BiJi'an
   * @date 2023-02-19 10:44
   */
  private void processPackageName(TemplateContext templateContext) {
    ClassInfo classInfo = templateContext.getClassInfo();
    TemplateStrategy strategy = templateContext.getTemplate().getStrategy();
    String packageName = strategy.getPackageName();
    String result = expressionExecutor.execute(packageName, templateContext.getContext());
    classInfo.setPackageName(result);
  }

  /**
   * @param templateContext templateContext
   * @return void
   * @title processClassName
   * @description
   * @author BiJi'an
   * @date 2023-02-19 10:44
   */
  private void processClassName(TemplateContext templateContext) {
    ClassInfo classInfo = templateContext.getClassInfo();
    TemplateStrategy strategy = templateContext.getTemplate().getStrategy();
    String className = strategy.getClassName();
    String result = expressionExecutor.execute(className, templateContext.getContext());
    classInfo.setName(result);
  }

  /**
   * @param templateContext templateContext
   * @return void
   * @title processSuperClass
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:21
   */
  private void processSuperClass(TemplateContext templateContext) {
    ClassInfo classInfo = templateContext.getClassInfo();
    TemplateStrategy strategy = templateContext.getTemplate().getStrategy();
    classInfo.setSuperClass(strategy.getSuperClass());
  }

  /**
   * @param templateContext templateContext
   * @return void
   * @title processSerilizeable
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:23
   */
  private void processInterfaces(TemplateContext templateContext) {
    ClassInfo classInfo = templateContext.getClassInfo();
    TemplateStrategy strategy = templateContext.getTemplate().getStrategy();
    classInfo.addInterfaces(strategy.getInterfaces());
  }

  /**
   * @param templateContext templateContext
   * @return void
   * @title processComment
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:28
   */
  private void processComment(TemplateContext templateContext) {
    ModuleInfo moduleInfo = templateContext.getModuleInfo();
    ClassInfo classInfo = templateContext.getClassInfo();
    TableMeta tableMeta = moduleInfo.getTableInfo().getTableMeta();
    classInfo.setComment(StringUtil.defaultString(tableMeta.getRemarks(), tableMeta.getName()));
  }
}
