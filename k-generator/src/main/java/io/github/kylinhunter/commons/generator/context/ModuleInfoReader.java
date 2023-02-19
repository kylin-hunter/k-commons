package io.github.kylinhunter.commons.generator.context;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.generator.context.bean.Column;
import io.github.kylinhunter.commons.generator.context.bean.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.TableInfo;
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
    private ColumnMetaReader columnMetaReader;

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
        tableInfo.setName(table.getName());

        List<String> skipColumns = table.getSkipColumns();
        List<ColumnMeta> columnMetas = columnMetaReader.getColumnMetaData(module.getDatabaseName(), table.getName());
        if (CollectionUtils.isEmpty(columnMetas)) {
            throw new CodeException("no column from table=>" + table);
        } else {
            List<Column> columns = columnMetas.stream().filter(c -> {
                if (skipColumns != null) {
                    return !skipColumns.contains(c.getColumnName());
                } else {
                    return true;
                }
            }).map(c -> new Column(c.getColumnName(), c.getJavaClass())).collect(Collectors.toList());
            tableInfo.setColumns(columns);
        }
        return tableInfo;
    }
}
