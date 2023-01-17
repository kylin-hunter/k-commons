package io.github.kylinhunter.commons.jdbc.meta.bean;

import io.github.kylinhunter.commons.exception.embed.ParamException;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 **/
@Data
public class DbMetaData {
    private String url;
    private String productName;
    private String version;
    private String driverName;
    private DbType dbType;

    public void setUrl(String url) {
        this.url = url;
        this.dbType = calDbType(url);
    }

    private DbType calDbType(String jdbcUrl) {

        if (jdbcUrl != null) {
            if (jdbcUrl.toLowerCase().contains("mysql")) {
                return DbType.MYSQL;
            } else {
                return DbType.OTHERS;
            }
        } else {
            throw new ParamException("jdbcUrl can't be null");
        }
    }
}
