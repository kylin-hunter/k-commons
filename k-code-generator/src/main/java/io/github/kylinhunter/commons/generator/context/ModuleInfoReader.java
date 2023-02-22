package io.github.kylinhunter.commons.generator.context;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.generator.context.bean.module.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.module.TableInfo;
import io.github.kylinhunter.commons.generator.exception.CodeException;
import io.github.kylinhunter.commons.jdbc.meta.ColumnMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.TableMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
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
    private ColumnMetaReader columnMetaReader;

    @CSet
    private TableMetaReader tableMetaReader;

    /**
     * @return io.github.kylinhunter.commons.generator.context.bean.ModuleInfos
     * @title read
     * @description
     * @author BiJi'an
     * @date 2023-02-18 23:26
     */
    public ModuleInfo read(Module module) {
        return new ModuleInfo(module, toTable(module));
    }

    /**
     * @param module module
     * @return io.github.kylinhunter.commons.generator.context.bean.Table
     * @title toTable
     * @description
     * @author BiJi'an
     * @date 2023-02-17 22:36
     */
    private TableInfo toTable(Module module) {
        TableInfo tableInfo = new TableInfo();
        Table table = module.getTable();

        TableMeta tableMetaData = tableMetaReader.getTableMetaData(module.getDatabaseName(), table.getName());
        ExceptionChecker.checkNotNull(tableMetaData, "tableMetaData can't be null");
        tableInfo.setTableMeta(tableMetaData);

        List<String> skipColumns = table.getSkipColumns();
        List<ColumnMeta> columnMetas = columnMetaReader.getColumnMetaData(module.getDatabaseName(), table.getName());
        if (CollectionUtils.isEmpty(columnMetas)) {
            throw new CodeException("no column from table=>" + table);
        } else {
            columnMetas = columnMetas.stream().filter(c -> {
                if (skipColumns != null) {
                    return !skipColumns.contains(c.getColumnName());
                } else {
                    return true;
                }
            }).collect(Collectors.toList());
            tableInfo.setColumnMetas(columnMetas);
        }
        return tableInfo;
    }
}
