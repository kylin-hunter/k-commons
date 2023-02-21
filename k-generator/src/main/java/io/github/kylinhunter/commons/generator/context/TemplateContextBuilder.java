package io.github.kylinhunter.commons.generator.context;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ClassUtils;

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
        processModule(templateContext);
        templateContext.putContext(templateContext.getTemplate().getContext());
        templateContext.putContext(templateContext.getModule().getContext());
        processPackageName(templateContext);
        processClassName(templateContext);
        processRemarks(templateContext);
        processSuperClass(templateContext);
        processSerilizeable(templateContext);
        processSwagger(templateContext);
        templateContext.putContext("class", templateContext.getClassInfo());
    }

    private void processModule(TemplateContext templateContext) {
        ClassInfo classInfo = templateContext.getClassInfo();
        templateContext.putContext(ContextConsts.MODULE, templateContext.getModuleInfo());

        templateContext.getModuleInfo().getTable().getColumns().forEach(c -> {
                    classInfo.addImportPackage(c.getClazz());

                }
        );
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

    private void processRemarks(TemplateContext templateContext) {
        ModuleInfo moduleInfo = templateContext.getModuleInfo();
        ClassInfo classInfo = templateContext.getClassInfo();

        classInfo.setRemarks(moduleInfo.getTable().getRemarks());
    }

    private void processSuperClass(TemplateContext templateContext) {
        ClassInfo classInfo = templateContext.getClassInfo();

        TemplateStrategy strategy = templateContext.getTemplate().getStrategy();
        String superClass = strategy.getSuperClass();

        String superClassName = ClassUtils.getShortClassName(superClass);
        classInfo.setSuperClassName(superClassName);
        classInfo.addImportPackage(superClass);

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
            classInfo.addImportPackage(ApiModel.class);
            classInfo.addImportPackage(ApiModelProperty.class);
            classInfo.addImportPackage(Api.class);
            classInfo.addImportPackage(ApiOperation.class);

        }
    }

    private void processSerilizeable(TemplateContext templateContext) {
        ClassInfo classInfo = templateContext.getClassInfo();
        TemplateStrategy strategy = templateContext.getTemplate().getStrategy();
        List<String> interfaces = strategy.getInterfaces();
        if (!CollectionUtils.isEmpty(interfaces)) {
            classInfo.addInterfaces(strategy.getInterfaces());

        }
    }
}
