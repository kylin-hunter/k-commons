package io.github.kylinhunter.commons.generator.context.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.ModuleTable;
import io.github.kylinhunter.commons.generator.context.bean.CodeContext;
import io.github.kylinhunter.commons.generator.context.bean.Column;
import io.github.kylinhunter.commons.generator.context.bean.ModuleContext;
import io.github.kylinhunter.commons.generator.context.bean.Table;
import io.github.kylinhunter.commons.generator.exception.CodeException;
import io.github.kylinhunter.commons.jdbc.meta.ColumnMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:24
 **/
@C(order = 1)
@RequiredArgsConstructor
public class ModuleContextHanlder implements ContextHandler {

    @CSet
    private Config config;

    @Override
    public void handle(CodeContext codeContext) {
        for (Module module : config.getModules()) {
            ModuleContext moduleContext = new ModuleContext(module);
            moduleContext.setDatabase(config.getGlobal().getDatabaseName());
            moduleContext.setTable( toTable(module.getTable()));

            codeContext.addModuleContext(moduleContext);
        }

    }

    /**
     * @param moduleTable moduleTable
     * @return io.github.kylinhunter.commons.generator.context.bean.Table
     * @title toTable
     * @description
     * @author BiJi'an
     * @date 2023-02-17 22:36
     */
    private Table toTable(ModuleTable moduleTable) {
        Table table = new Table();
        table.setName(moduleTable.getName());
        Global global = config.getGlobal();
        String databaseName = global.getDatabaseName();

        List<String> skipColumns = moduleTable.getSkipColumns();
        ColumnMetaReader columnMetaReader = CF.get(ColumnMetaReader.class);

        List<ColumnMeta> columnMetas = columnMetaReader.getColumnMetaData(databaseName, moduleTable.getName());
        if (CollectionUtils.isEmpty(columnMetas)) {
            throw new CodeException("no column from table=>" + moduleTable);
        } else {
            List<Column> columns = columnMetas.stream().filter(c -> {
                if (skipColumns != null) {
                    return !skipColumns.contains(c.getColumnName());
                } else {
                    return true;
                }
            }).map(c -> new Column(c.getColumnName(), c.getJavaClass())).collect(Collectors.toList());
            table.setColumns(columns);
        }
        return table;
    }
}
