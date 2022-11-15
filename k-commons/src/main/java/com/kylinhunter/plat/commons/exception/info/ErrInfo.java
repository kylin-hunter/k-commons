package com.kylinhunter.plat.commons.exception.info;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Data
@AllArgsConstructor
public class ErrInfo {
    private int classCode;
    private int code;
    private String defaultMsg;

    public ErrInfo(ErrInfoClassify errInfoClassify) {
        this.classCode = errInfoClassify.getCode();
        this.code = errInfoClassify.next();
    }

    public ErrInfo(ErrInfoClassify errInfoClassify, String defaultMsg) {
        this.classCode = errInfoClassify.getCode();
        this.code = errInfoClassify.next();
        this.defaultMsg = defaultMsg;
    }

    public ErrInfo(int code, String defaultMsg) {
        this.code = code;
        this.defaultMsg = defaultMsg;
    }
}
