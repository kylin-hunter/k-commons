package com.kylinhunter.plat.commons.exception.inner;

import com.kylinhunter.plat.commons.exception.common.KRuntimeException;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class ParamException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public ParamException() {
        this.errInfo = ErrInfos.PARAM;
    }

    public ParamException(String message) {
        super(ErrInfos.PARAM, message);
    }

    public ParamException(String message, Throwable cause) {
        super(ErrInfos.PARAM, message, cause);
    }
}
