package io.github.kylinhunter.commons.generator.context;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.config.bean.Global;
import io.github.kylinhunter.commons.generator.config.bean.ModuleInfo;
import io.github.kylinhunter.commons.generator.config.bean.TableInfo;
import io.github.kylinhunter.commons.generator.context.bean.Column;
import io.github.kylinhunter.commons.generator.context.bean.Module;
import io.github.kylinhunter.commons.generator.context.bean.Modules;
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
@C
@RequiredArgsConstructor
public class ModuleInfoReader {

    @CSet
    private Config config;

    /**
     * @return io.github.kylinhunter.commons.generator.context.bean.ModuleInfos
     * @title read
     * @description
     * @author BiJi'an
     * @date 2023-02-18 23:26
     */
    public Modules read() {
        Modules modules = new Modules();
        for (ModuleInfo module : config.getModules()) {
            Module
                    moduleInfo = new Module(module);
            moduleInfo.setDatabase(config.getGlobal().getDatabaseName());
            moduleInfo.setTable(toTable(module.getTable()));
            modules.add(moduleInfo);
        }
        return modules;

    }

    /**
     * @param tableInfo moduleTable
     * @return io.github.kylinhunter.commons.generator.context.bean.Table
     * @title toTable
     * @description
     * @author BiJi'an
     * @date 2023-02-17 22:36
     */
    private Table toTable(TableInfo tableInfo) {
        Table table = new Table();
        table.setName(tableInfo.getName());
        Global global = config.getGlobal();
        String databaseName = global.getDatabaseName();

        List<String> skipColumns = tableInfo.getSkipColumns();
        ColumnMetaReader columnMetaReader = CF.get(ColumnMetaReader.class);
        List<ColumnMeta> columnMetas = columnMetaReader.getColumnMetaData(databaseName, tableInfo.getName());
        if (CollectionUtils.isEmpty(columnMetas)) {
            throw new CodeException("no column from table=>" + tableInfo);
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
