package io.github.kylinhunter.commons.exception.builtin;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class SystemException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public SystemException() {
        this.errInfo = ErrInfos.SYSTEM;
    }

    public SystemException(String message) {
        super(ErrInfos.SYSTEM, message);
    }

    public SystemException(String message, Throwable cause) {
        super(ErrInfos.SYSTEM, message, cause);
    }
}
