package com.kylinhunter.plat.commons.exception.explain;

import com.kylinhunter.plat.commons.exception.common.KThrowable;
import com.kylinhunter.plat.commons.exception.info.ErrInfo;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-19 18:59
 **/

@Data
public class ExplainInfo {
    private ErrInfo errInfo;
    private Object extra;
    private String msg;

    public ExplainInfo(KThrowable KThrowable, String msg) {
        this.errInfo = KThrowable.getErrInfo();
        if (this.errInfo == null) {
            this.errInfo = ErrInfos.UNKNOWN;
        }
        this.extra = KThrowable.getExtra();
        this.msg = msg;
    }

    public ExplainInfo(ErrInfo errInfo, String msg) {
        this.errInfo = errInfo;
        if (this.errInfo == null) {
            this.errInfo = ErrInfos.UNKNOWN;
        }
        this.msg = msg;
    }

    public String getMsg() {
        if (this.msg != null && this.msg.length() > 0) {
            return this.msg;
        } else {
            return this.errInfo.getDefaultMsg();
        }
    }

}
