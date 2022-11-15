package com.kylinhunter.plat.commons.exception.common;

import com.kylinhunter.plat.commons.exception.info.ErrInfo;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Getter
@Setter
public class KRuntimeException extends RuntimeException implements KThrowable {
    private static final long serialVersionUID = 1L;
    protected ErrInfo errInfo = ErrInfos.UNKNOWN;
    private Object extra;

    public KRuntimeException() {
        super();
    }

    public KRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public KRuntimeException(String message) {
        super(message);
    }

    public KRuntimeException(Throwable cause) {
        super(cause);
    }

    public KRuntimeException(ErrInfo errInfo, String message, Throwable cause) {
        super(message, cause);
        this.errInfo = errInfo;
    }

    public KRuntimeException(ErrInfo errInfo, Object extra, String message, Throwable cause) {
        super(message, cause);
        this.errInfo = errInfo;
        this.extra = extra;
    }

    public KRuntimeException(ErrInfo errInfo, String message) {
        super(message);
        this.errInfo = errInfo;
    }

    public KRuntimeException(ErrInfo errInfo, Object extra, String message) {
        super(message);
        this.errInfo = errInfo;
        this.extra = extra;
    }

    public KRuntimeException(ErrInfo errInfo, Throwable cause) {
        super(cause);
        this.errInfo = errInfo;
    }

}
