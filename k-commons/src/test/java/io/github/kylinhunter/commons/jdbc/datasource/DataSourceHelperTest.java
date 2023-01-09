package io.github.kylinhunter.commons.jdbc.datasource;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.junit.jupiter.api.Test;

class DataSourceHelperTest {

    @Test
    void test() throws SQLException {
        QueryRunner qr = new QueryRunner(DataSourceHelper.defaultDataSource);
        // 2.执行查询
        List<Object[]> objs = qr.query(" select * from kylin_user limit 10 ", new ArrayListHandler());
        for (Object[] os : objs) {        // object[]中保存了object对象
            for (Object o : os) {
                System.out.print(o + "\t");
            }
            System.out.println();
        }
    }
}