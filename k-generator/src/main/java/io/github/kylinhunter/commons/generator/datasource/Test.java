package io.github.kylinhunter.commons.generator.datasource;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayListHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-08 16:18
 **/
public class Test {
    public static void main(String[] args) throws SQLException {
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