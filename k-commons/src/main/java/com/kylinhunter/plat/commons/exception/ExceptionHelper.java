package com.kylinhunter.plat.commons.exception;

import org.apache.commons.lang3.StringUtils;

import com.kylinhunter.plat.commons.exception.common.KThrowable;
import com.kylinhunter.plat.commons.exception.info.ErrInfo;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;
import com.kylinhunter.plat.commons.exception.info.ErrInfoManager;

/**
 * @author BiJi'an
 * @description 异常的工具类
 * @date 2022/01/01
 **/
public class ExceptionHelper {

    /**
     * @param e
     * @return int
     * @throws
     * @title 获取异常代码
     * @description
     * @author BiJi'an
     * @date 2022/01/01 5:22 下午
     */
    public static int getErrCode(Throwable e) {
        if (e instanceof KThrowable) {
            ErrInfo errInfo = ((KThrowable) e).getErrInfo();
            if (errInfo != null) {
                return errInfo.getCode();
            }
        }
        return ErrInfos.CODE_UNKNOWN;

    }

    /**
     * @param e
     * @return java.lang.String
     * @throws
     * @title 获取异常消息
     * @description
     * @author BiJi'an
     * @date 2022/01/01 5:22 下午
     */
    public static String getMessage(Throwable e) {
        return getMessage(e, false, 100);

    }

    /**
     * @param e
     * @param showUnknownMsg
     * @param max
     * @return java.lang.String
     * @throws
     * @title 获取异常消息
     * @description
     * @author BiJi'an
     * @date 2022/01/01 5:23 下午
     */
    public static String getMessage(Throwable e, boolean showUnknownMsg, int max) {

        if (e instanceof KThrowable) {
            ErrInfo errInfo = ((KThrowable) e).getErrInfo();
            if (errInfo != null) {
                int code = errInfo.getCode();
                if (code != ErrInfos.UNKNOWN.getCode()) {
                    String msg = StringUtils.defaultIfBlank(e.getMessage(), ErrInfoManager.getDefaultMsg(code));
                    return StringUtils.substring(msg, 0, max);
                }
            }

        }
        String returnMsg = "";
        if (showUnknownMsg) {
            returnMsg = StringUtils.defaultString(e.getMessage(), ErrInfos.MSG_UNKNOWN);
        } else {
            returnMsg = ErrInfos.MSG_UNKNOWN;
        }

        return StringUtils.substring(returnMsg, 0, max);

    }

}
