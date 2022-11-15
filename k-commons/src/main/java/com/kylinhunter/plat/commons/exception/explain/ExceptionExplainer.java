package com.kylinhunter.plat.commons.exception.explain;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kylinhunter.plat.commons.exception.explain.ExceptionFinder.ExceptionFind;
import com.kylinhunter.plat.commons.exception.common.KRuntimeException;
import com.kylinhunter.plat.commons.exception.common.KThrowable;
import com.kylinhunter.plat.commons.exception.info.ErrInfos;

import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2021/8/1
 **/
@Slf4j
public class ExceptionExplainer {

    private final Map<Class<? extends Throwable>, Explain<Throwable>> exceptionExplains =
            Maps.newHashMap();
    public Set<Class<? extends Throwable>> allExceptions = Sets.newHashSet();

    /**
     * @param cls              cls
     * @param explain explain
     * @title register
     * @description
     * @author BiJi'an
     * @date 2022-01-01 01:25
     */

    @SuppressWarnings("unchecked")
    public <T extends Throwable> void register(Class<T> cls, Explain<T> explain) {
        exceptionExplains.put(cls, (Explain<Throwable>) explain);
        allExceptions.add(cls);
    }

    /**
     * @param throwable throwable
     * @return com.kylinhunter.plat.commons.exception.explain.ExplainInfo
     * @title explain explain
     * @description
     * @author BiJi'an
     * @date 2022-05-18 00:31
     */

    private ExplainInfo explain(Throwable throwable) {
        ExplainInfo explainInfo = null;
        if (throwable instanceof KThrowable) {
            explainInfo = new ExplainInfo((KThrowable) throwable, throwable.getMessage());
        } else {
            ExceptionFind exceptionFind = ExceptionFinder.find(throwable, true, allExceptions);
            if (exceptionFind != null) {
                Explain<Throwable> explain = exceptionExplains.get(exceptionFind.getSource());
                if (explain != null) {
                    explainInfo = explain.explain(exceptionFind.getTarget());
                }
            }
            if (explainInfo == null) {
                explainInfo = new ExplainInfo(ErrInfos.UNKNOWN, throwable.getMessage());
            }
        }
        return explainInfo;
    }



    /**
     * @param exception exception
     * @return KRuntimeException
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2022-05-18 00:30
     */
    public KRuntimeException convert(Throwable exception) {
        return convert(exception, true);
    }

    /**
     * @param exception exception
     * @param trace     trace
     * @return KRuntimeException
     * @title convert
     * @description
     * @author BiJi'an
     * @date 2022-05-18 00:30
     */
    public KRuntimeException convert(Throwable exception, boolean trace) {
        if (KRuntimeException.class.isAssignableFrom(exception.getClass())) {
            return (KRuntimeException) exception;
        } else {
            try {
                ExplainInfo explainInfo = this.explain(exception);
                if (trace) {
                    return new KRuntimeException(explainInfo.getErrInfo(), explainInfo.getExtra(),
                            explainInfo.getMsg(), exception);
                } else {
                    return new KRuntimeException(explainInfo.getErrInfo(), explainInfo.getExtra(),
                            explainInfo.getMsg());
                }
            } catch (Exception e) {
                log.error("convert error", e);
            }
            return new KRuntimeException(ErrInfos.UNKNOWN, exception.getMessage());
        }

    }
}
