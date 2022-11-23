package io.github.kylinhunter.commons.exception.builtin.biz;

import io.github.kylinhunter.commons.exception.info.ErrInfo;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import io.github.kylinhunter.commons.exception.builtin.biz.BizException;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class DBException extends BizException {
    private static final long serialVersionUID = 1L;

    public DBException() {
        this.errInfo = ErrInfos.DB;
    }

    public DBException(ErrInfo errInfo, Throwable cause) {
        super(errInfo, cause);
    }

    public DBException(ErrInfo errInfo, String message) {
        super(errInfo, message);
    }

    public DBException(String message) {
        super(ErrInfos.DB, message);
    }

    public DBException(String message, Throwable cause) {
        super(ErrInfos.DB, message, cause);
    }

}
