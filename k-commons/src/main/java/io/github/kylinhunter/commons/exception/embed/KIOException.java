package io.github.kylinhunter.commons.exception.embed;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.info.ErrInfos;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/

public class KIOException extends KRuntimeException {
    private static final long serialVersionUID = 1L;

    public KIOException() {
        this.errInfo = ErrInfos.IO;
    }

    public KIOException(String message) {
        super(ErrInfos.IO, message);
    }

    public KIOException(String message, Throwable e) {
        super(ErrInfos.IO, message, e);
    }
}
