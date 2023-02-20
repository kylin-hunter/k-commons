package io.github.kylinhunter.commons.generator.context;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Modules;
import io.github.kylinhunter.commons.generator.config.bean.Template;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.constant.ContextConsts;
import io.github.kylinhunter.commons.generator.context.bean.ClassInfo;
import io.github.kylinhunter.commons.generator.context.bean.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.generator.function.ExpressionExecutor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:24
 **/
@C
@RequiredArgsConstructor
@Slf4j
public class TemplateContextBuilder {

    @CSet
    private Config config;
    @CSet
    private ModuleInfoReader moduleInfoReader;
    @CSet
    private ExpressionExecutor expressionExecutor;

    public List<TemplateContext> calculateContext() {
        List<TemplateContext> templateContexts = Lists.newArrayList();
        Modules modules = config.getModules();
        for (Module module : modules.getList()) {
            ModuleInfo moduleInfo = moduleInfoReader.read(module);
            for (Template template : config.getTemplates().getList()) {
                TemplateContext templateContext = new TemplateContext(moduleInfo, module, template);
                calculateContext(templateContext);
                templateContexts.add(templateContext);
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
     * @date 2023-02-18 00:23
     */
    private void calculateContext(TemplateContext templateContext) {
        templateContext.putContext(ContextConsts.MODULE, templateContext.getModuleInfo());
        templateContext.putContext(templateContext.getTemplate().getContext());
        templateContext.putContext(templateContext.getModule().getContext());
        processPackageName(templateContext);
        processClassName(templateContext);
        processSerilizeable(templateContext);
        processLombok(templateContext);
        processSwagger(templateContext);
        templateContext.resetClassInfo();
        templateContext.putContext("classinfo", templateContext.getClassInfo());
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
        TemplateStrategy strategy = templateContext.getTemplate().getStrategy();
        String packageName = strategy.getPackageName();
        String result = expressionExecutor.execute(packageName, templateContext.getContext());
        templateContext.setPackageName(result);

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
        TemplateStrategy strategy = templateContext.getTemplate().getStrategy();
        String className = strategy.getClassName();
        String result = expressionExecutor.execute(className, templateContext.getContext());
        templateContext.setClassName(result);
    }

    /**
     * @param templateContext templateContext
     * @return void
     * @title processLombok
     * @description
     * @author BiJi'an
     * @date 2023-02-19 14:29
     */
    private void processLombok(TemplateContext templateContext) {
        ClassInfo classInfo = templateContext.getClassInfo();
        if (templateContext.isLombokEnabled()) {
            classInfo.addAnnotation(lombok.Data.class);
            classInfo.addAnnotation(lombok.EqualsAndHashCode.class);
        }
    }

    /**
     * @param templateContext templateContext
     * @return void
     * @title processSwagger
     * @description
     * @author BiJi'an
     * @date 2023-02-19 14:51
     */
    private void processSwagger(TemplateContext templateContext) {
        ClassInfo classInfo = templateContext.getClassInfo();
        if (templateContext.isWaggerEnabled()) {
            classInfo.addImportPackage(ApiModel.class.getName());
            classInfo.addImportPackage(ApiModelProperty.class.getName());
            classInfo.addImportPackage(Api.class.getName());
            classInfo.addImportPackage(ApiOperation.class.getName());

        }
    }

    private void processSerilizeable(TemplateContext templateContext) {
        ClassInfo classInfo = templateContext.getClassInfo();

        classInfo.addImportPackage(Serializable.class);
    }
}
