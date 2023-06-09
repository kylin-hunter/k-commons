package io.github.kylinhunter.commons.exception;

import io.github.kylinhunter.commons.exception.common.KThrowable;
import io.github.kylinhunter.commons.exception.info.ErrInfo;
import io.github.kylinhunter.commons.exception.info.ErrInfoManager;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import io.github.kylinhunter.commons.lang.strings.StringUtil;

/**
 * @author BiJi'an
 * @description 异常的工具类
 * @date 2022/01/01
 */
public class ExceptionHelper {

    /**
     * @param e e
     * @return int
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
     * @param e e
     * @return java.lang.String
     * @title 获取异常消息
     * @description
     * @author BiJi'an
     * @date 2022/01/01 5:22 下午
     */
    public static String getMessage(Throwable e) {
        return getMessage(e, false, 100);
    }

    /**
     * @param e              e
     * @param showUnknownMsg showUnknownMsg
     * @param max            max
     * @return java.lang.String
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
                    String msg =
                            StringUtil.defaultIfBlank(e.getMessage(), ErrInfoManager.getDefaultMsg(code));
                    return StringUtil.substring(msg, 0, max);
                }
            }
        }
        String returnMsg;
        if (showUnknownMsg) {
            returnMsg = StringUtil.defaultString(e.getMessage(), ErrInfos.MSG_UNKNOWN);
        } else {
            returnMsg = ErrInfos.MSG_UNKNOWN;
        }

        return StringUtil.substring(returnMsg, 0, max);
    }
}
