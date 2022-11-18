package io.github.kylinhunter.commons.exception.common;

import io.github.kylinhunter.commons.exception.info.ErrInfo;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public interface KThrowable {
    ErrInfo getErrInfo();

    Object getExtra();
}
