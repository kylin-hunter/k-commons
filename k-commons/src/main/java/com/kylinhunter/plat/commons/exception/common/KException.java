package com.kylinhunter.plat.commons.exception.common;

import com.kylinhunter.plat.commons.exception.info.ErrInfo;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

import lombok.Getter;
import lombok.Setter;

/**
 * @description 
 * @author  BiJi'an
 * @date 2022/1/1
 **/
@Getter
@Setter
public class KException extends Exception implements KThrowable {
    private static final long serialVersionUID = 1L;
    private ErrInfo errInfo = ErrInfos.UNKNOWN;
    private Object extra;

    public KException() {
    }

    public KException(String message, Throwable cause) {
        super(message, cause);
    }

    public KException(String message) {
        super(message);
    }

    public KException(Throwable cause) {
        super(cause);
    }

    public KException(ErrInfo errInfo, String message, Throwable cause) {
        super(message, cause);
        this.errInfo = errInfo;

    }

    public KException(ErrInfo errInfo, String message) {
        super(message);
        this.errInfo = errInfo;

    }

    public KException(ErrInfo errInfo, Throwable cause) {
        super(cause);
        this.errInfo = errInfo;

    }

}
