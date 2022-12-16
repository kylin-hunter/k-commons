package io.github.kylinhunter.commons.bean.copy.convertor;

import io.github.kylinhunter.commons.exception.embed.biz.BizException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-10 00:05
 **/
public class ConvertExcetion extends BizException {

    public ConvertExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertExcetion(String message) {
        super(message);
    }
}
