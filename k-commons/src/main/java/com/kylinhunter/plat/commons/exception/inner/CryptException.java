package com.kylinhunter.plat.commons.exception.inner;

import com.kylinhunter.plat.commons.exception.common.KRuntimeException;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/

public class CryptException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public CryptException() {
        this.errInfo = ErrInfos.CRYPT;
    }

    public CryptException(String message) {
        super(ErrInfos.CRYPT, message);
    }

    public CryptException(String message, Throwable e) {
        super(ErrInfos.CRYPT, message, e);
    }
}
