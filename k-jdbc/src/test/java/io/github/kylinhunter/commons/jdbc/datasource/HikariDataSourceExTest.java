package io.github.kylinhunter.commons.jdbc.datasource;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.Test;

class HikariDataSourceExTest {

    @Test
    void test() throws SQLException {
        DataSource defaultDataSource = DataSourceUtils.getDefaultDataSource();

        QueryRunner qr = new QueryRunner(defaultDataSource);

//        // 2.执行查询
//        List<Object[]> objs = qr.query(" desc kylin_user", new ArrayListHandler());
//        for (Object[] os : objs) {        // object[]中保存了object对象
//            for (Object o : os) {
//                System.out.print(o + "\t");
//            }
//            System.out.println();
//        }
    }
}