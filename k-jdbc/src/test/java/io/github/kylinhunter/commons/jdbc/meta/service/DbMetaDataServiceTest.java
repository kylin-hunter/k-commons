package io.github.kylinhunter.commons.jdbc.meta.service;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.jdbc.meta.MetaDataService;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetaData;
import io.github.kylinhunter.commons.jdbc.meta.bean.DbMetaData;

class DbMetaDataServiceTest {

    @Test
    void test() throws SQLException {
        MetaDataService metaService = new MetaDataService();
        DbMetaData databaseMetaData = metaService.getDatabaseMetaData();
        System.out.println(databaseMetaData);
        List<ColumnMetaData> columnMetaDatas = metaService.getColumnMetaData("kp", "test_kylin_user");
        for (ColumnMetaData columnMetaData : columnMetaDatas) {
            System.out.println(columnMetaData);

        }
    }
}