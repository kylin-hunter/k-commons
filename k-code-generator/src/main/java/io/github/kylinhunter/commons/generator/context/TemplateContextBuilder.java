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
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:24
 */
@Slf4j
public class TemplateContextBuilder {

  private final Config config;
  private final ModuleInfoReader moduleInfoReader;
  private final ExpressionExecutor expressionExecutor = new ExpressionExecutor();
  private final FieldInfoConvertor fieldInfoConvertor = new FieldInfoConvertor();

  public TemplateContextBuilder(Config config, DataSource dataSource) {
    this.config = config;
    this.moduleInfoReader = new ModuleInfoReader(dataSource);
  }

  public TemplateContextBuilder(Config config, ModuleInfoReader moduleInfoReader) {
    this.config = config;
    this.moduleInfoReader = moduleInfoReader;
  }

  /**
   * @return java.util.List<io.github.kylinhunter.commons.generator.context.bean.TemplateContext>
   * @title calculateContext
   * @description calculateContext
   * @author BiJi'an
   * @date 2023-12-17 16:07
   */
  public List<TemplateContext> build() {
    List<TemplateContext> templateContexts = ListUtils.newArrayList();
    Modules modules = config.getModules();
    for (Module module : modules.getList()) {
      ModuleInfo moduleInfo = moduleInfoReader.read(module);
      for (Template template : config.getTemplates().getList()) {
        if (template.isEnabled()) {
          TemplateContext templateContext = new TemplateContext(moduleInfo, template);
          build(templateContext);
          templateContexts.add(templateContext);
        }
      }
    }

    return templateContexts;
  }

  /**
   * @param templateContext templateContext
   * @title calculateContext
   * @description calculateContext
   * @author BiJi'an
   * @date 2023-12-12 01:10
   */
  private void build(TemplateContext templateContext) {
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
