package io.github.kylinhunter.commons.generator.function;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.context.bean.Column;
import io.github.kylinhunter.commons.generator.context.bean.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.TableInfo;

class StringToCamelTest {

    @Test
    void execute() {
        ExpressionExecutor expressionExecutor = CF.get(ExpressionExecutor.class);

        ModuleInfo moduleInfo = new ModuleInfo();
        moduleInfo.setName("hello_the_word");
        TableInfo tableInfo = new TableInfo();
        tableInfo.setName("tableName");
        List<Column> columns = Lists.newArrayList();
        Column column = new Column();
        column.setName("columnName");
        column.setClazz(List.class);
        columns.add(column);
        tableInfo.setColumns(columns);

        moduleInfo.setTable(tableInfo);
        Map<String, Object> env = Maps.newHashMap();
        env.put("module", moduleInfo);

        Object execute = expressionExecutor.execute("k.string_to_camel(module.name,'lower')", env);
        System.out.println(execute);

        execute = expressionExecutor.execute("k.string_to_camel(module.name,'upper')", env);
        System.out.println(execute);

    }
}