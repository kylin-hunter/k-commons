package com.kylinhunter.plat.commons.exception.inner;

import com.kylinhunter.plat.commons.exception.common.KRuntimeException;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class InternalException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public InternalException() {
        this.errInfo = ErrInfos.INTERNAL;
    }

    public InternalException(String message, Throwable cause) {
        super(ErrInfos.INTERNAL, message, cause);
    }

    public InternalException(String message) {
        super(ErrInfos.INTERNAL, message);
    }

    public InternalException(Throwable cause) {
        super(ErrInfos.INTERNAL, cause);
    }

}
