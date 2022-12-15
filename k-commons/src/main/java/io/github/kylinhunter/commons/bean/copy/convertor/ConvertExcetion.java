package io.github.kylinhunter.commons.bean.copy.convertor;

import io.github.kylinhunter.commons.exception.common.KException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-16 00:05
 **/
public class ConvertExcetion extends KException {

    public ConvertExcetion(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertExcetion(String message) {
        super(message);
    }
}
