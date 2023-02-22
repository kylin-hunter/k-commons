package io.github.kylinhunter.commons.jdbc.exception;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;

/**
 * @author BiJi'an
 * @description JdbcException
 * @date 2023-01-17 23:47
 **/
public class JdbcException extends BizException {
    public JdbcException(String message, Throwable cause) {
        super(message, cause);
    }
}
