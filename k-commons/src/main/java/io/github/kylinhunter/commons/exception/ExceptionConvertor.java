package io.github.kylinhunter.commons.exception;

import io.github.kylinhunter.commons.exception.common.KRuntimeException;
import io.github.kylinhunter.commons.exception.explain.ExplainManager;
import io.github.kylinhunter.commons.exception.explain.Explainer;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2021/8/1
 **/
@Slf4j
public class ExceptionConvertor {



    /**
     * @param exception source
     * @return KRuntimeException
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2022-05-18 00:30
     */
    public static KRuntimeException convert(Throwable exception) {
        return convert(exception, true);
    }

    /**
     * @param exception source
     * @param withCause     withCause
     * @return KRuntimeException
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2022-05-18 00:30
     */
    public static KRuntimeException convert(Throwable exception, boolean withCause) {
        if (KRuntimeException.class.isAssignableFrom(exception.getClass())) {
            return (KRuntimeException) exception;
        } else {
            try {
                Explainer.ExplainResult explainResult = ExplainManager.explain(exception);
                if (withCause) {
                    return new KRuntimeException(explainResult.getErrInfo(), explainResult.getExtra(),
                            explainResult.getMsg(), exception);
                } else {
                    return new KRuntimeException(explainResult.getErrInfo(), explainResult.getExtra(),
                            explainResult.getMsg());
                }
            } catch (Exception e) {
                log.error("convert error", e);
            }
            return new KRuntimeException(ErrInfos.UNKNOWN, exception.getMessage());
        }

    }
}
