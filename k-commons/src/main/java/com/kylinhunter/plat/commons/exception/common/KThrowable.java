package com.kylinhunter.plat.commons.exception.common;

import com.kylinhunter.plat.commons.exception.info.ErrInfo;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
public interface KThrowable {
    ErrInfo getErrInfo();

    Object getExtra();
}
