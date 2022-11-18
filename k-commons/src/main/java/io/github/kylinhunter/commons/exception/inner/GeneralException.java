package io.github.kylinhunter.commons.exception.inner;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public class GeneralException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public GeneralException() {
        this.errInfo = ErrInfos.GENERAL;
    }

    public GeneralException(String message) {
        super(ErrInfos.GENERAL, message);
    }

    public GeneralException(String message, Throwable cause) {
        super(ErrInfos.GENERAL, message, cause);
    }
}
