package io.github.kylinhunter.commons.generator.function;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.context.bean.module.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.module.TableInfo;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;

class StringToCamelTest {

    @Test
    void execute() {
        ExpressionExecutor expressionExecutor = CF.get(ExpressionExecutor.class);

        ModuleInfo moduleInfo = new ModuleInfo();
        moduleInfo.setName("hello_the_word");
        TableInfo tableInfo = new TableInfo();
        TableMeta tableMeta=new TableMeta();
        tableMeta.setName("tableName");
        tableInfo.setTableMeta(tableMeta);

        List<ColumnMeta> columns = Lists.newArrayList();
        ColumnMeta column = new ColumnMeta();
        column.setColumnName("columnName");
        column.setJavaClass(List.class);
        columns.add(column);
        tableInfo.setColumnMetas(columns);

        moduleInfo.setTableInfo(tableInfo);
        Map<String, Object> env = Maps.newHashMap();
        env.put("module", moduleInfo);

        Object execute = expressionExecutor.execute("k.str_camel(module.name,'lower')", env);
        System.out.println(execute);

        execute = expressionExecutor.execute("k.str_camel(module.name,'upper')", env);
        System.out.println(execute);

    }
}