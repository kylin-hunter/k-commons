package com.kylinhunter.plat.commons.exception.inner;

import com.kylinhunter.plat.commons.exception.common.KRuntimeException;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class InitException extends KRuntimeException {

    private static final long serialVersionUID = 1L;

    public InitException() {
        this.errInfo = ErrInfos.INIT;
    }

    public InitException(String message, Throwable cause) {
        super(ErrInfos.INIT, message, cause);
    }

    public InitException(String message) {
        super(ErrInfos.INIT, message);
    }

    public InitException(Throwable cause) {
        super(ErrInfos.INIT, cause);
    }

}
