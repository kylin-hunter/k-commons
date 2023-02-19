package io.github.kylinhunter.commons.generator.context;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.TemplateConfig;
import io.github.kylinhunter.commons.generator.config.bean.TemplateStrategy;
import io.github.kylinhunter.commons.generator.constant.ContextConsts;
import io.github.kylinhunter.commons.generator.context.bean.Module;
import io.github.kylinhunter.commons.generator.context.bean.Modules;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContext;
import io.github.kylinhunter.commons.generator.context.bean.TemplateContexts;
import io.github.kylinhunter.commons.generator.function.ExpressionExecutor;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:24
 **/
@C(order = 2)
@RequiredArgsConstructor
public class TemplateContextBuilder {

    @CSet
    private Config config;
    @CSet
    private ModuleInfoReader moduleInfoReader;

    public TemplateContexts build() {
        Modules modules = moduleInfoReader.read();
        TemplateContexts templateContexts = new TemplateContexts();
        List<TemplateConfig> templates = config.getTemplates().getList();
        for (TemplateConfig templateConfig : templates) {
            for (Module module : modules.getAll()) {
                templateContexts.add(toTemplateContext(module, templateConfig));
            }
        }
        return templateContexts;
    }

    /**
     * @param module         moduleContext
     * @param templateConfig templateConfig
     * @return io.github.kylinhunter.commons.generator.context.bean.TemplateContext
     * @title toTemplateContext
     * @description
     * @author BiJi'an
     * @date 2023-02-18 00:23
     */
    private TemplateContext toTemplateContext(Module module, TemplateConfig templateConfig) {
        TemplateContext templateContext = new TemplateContext(module, templateConfig);

        TemplateStrategy strategy = templateContext.getTemplateConfig().getStrategy();
        String packageName = strategy.getPackageName();
        String className = strategy.getClassName();
        String moduleName = module.getName();

        processContext(templateContext, module);
        templateContext.putContext(templateConfig.getContext());

        templateContext.putContext(ContextConsts.PACKAGE_NAME, ExpressionExecutor.execute(packageName,
                templateContext.getContext()));
        templateContext
                .putContext(ContextConsts.CLASS_NAME, ExpressionExecutor.execute(className,
                        templateContext.getContext()));

        return templateContext;

    }

    private void processContext(TemplateContext templateContext, Module module) {
        //        templateContext.putContext(ContextConsts.MODULE_NAME, module.getName());
        //        Table table = module.getTable();
        //        templateContext.putContext(ContextConsts.MODULE_TABLE_NAME, table.getName());
        //        List<Column> columns = table.getColumns();
        //        templateContext.putContext(ContextConsts.MODULE_TABLE_COLUMNS, columns);
        //        for (Column column : columns) {
        //            String key1 = ContextConsts.MODULE_TABLE_COLUMN_PREFIX + column.getName();
        //            templateContext.putContext(key1, column);
        //
        //            String key2 = ContextConsts.MODULE_TABLE_COLUMN_PREFIX + column.getName() + ".javaClass";
        //            templateContext.putContext(key2, column.getClazz().getName());
        //
        //        }

        templateContext.putContext("module", module);

        templateContext.putContext(module.getContext());

    }

    /**
     * @param module           module
     * @param classNamePattern classNamePattern
     * @return java.lang.String
     * @title getClassName
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:16
     */
    private String getClassName(Module module, String classNamePattern) {
        Map<String, Object> env = Maps.newHashMap();
        env.put("module.name", "hello_the_word");

        return ExpressionExecutor.execute(classNamePattern, env);

    }

}
