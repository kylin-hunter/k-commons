package io.github.kylinhunter.commons.generator.context;

import java.util.List;

import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.generator.config.bean.Config;
import io.github.kylinhunter.commons.generator.context.bean.CodeContext;
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
public class JDBCContextHanlder implements Handler {
    @CSet
    private Config config;
    @Override
    public void handle(CodeContext codeContext) {
        ColumnMetaReader columnMetaReader = CF.get(ColumnMetaReader.class);

        List<ColumnMeta> columnMetas = columnMetaReader.getColumnMetaData("kp", "test_user");
        for (ColumnMeta columnMeta : columnMetas) {
            System.out.println(columnMeta);
            System.out.println(columnMeta.getColumnName() + ":" + columnMeta.getJavaClass().getName());
        }
    }
}
