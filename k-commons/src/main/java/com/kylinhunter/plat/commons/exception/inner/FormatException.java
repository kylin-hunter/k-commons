package com.kylinhunter.plat.commons.exception.inner;

import com.kylinhunter.plat.commons.exception.common.KRuntimeException;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/

public class FormatException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public FormatException() {
        this.errInfo = ErrInfos.FORMAT;
    }

    public FormatException(String message) {
        super(ErrInfos.FORMAT, message);
    }

    public FormatException(String message, Throwable e) {
        super(ErrInfos.FORMAT, message, e);
    }
}
