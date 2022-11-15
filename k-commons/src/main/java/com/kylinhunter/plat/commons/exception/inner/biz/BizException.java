package com.kylinhunter.plat.commons.exception.inner.biz;

import com.kylinhunter.plat.commons.exception.common.KRuntimeException;
import com.kylinhunter.plat.commons.exception.info.ErrInfo;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class BizException extends KRuntimeException {

    private static final long serialVersionUID = 1L;

    public BizException() {
        this.errInfo = ErrInfos.BIZ;
    }

    public BizException(String message, Throwable cause) {
        this(ErrInfos.BIZ, message, cause);
    }

    public BizException(String message) {
        this(ErrInfos.BIZ, message);
    }

    public BizException(Throwable cause) {
        this(ErrInfos.BIZ, cause);
    }

    public BizException(ErrInfo errInfo, String message, Throwable cause) {
        super(errInfo, message, cause);
    }

    public BizException(ErrInfo errInfo, String message) {
        super(errInfo, message);
    }

    public BizException(ErrInfo errInfo, Object extra) {
        super(errInfo, "");
        this.setExtra(extra);
    }

    public BizException(ErrInfo errInfo, Throwable cause) {
        super(errInfo, cause);
    }

}
