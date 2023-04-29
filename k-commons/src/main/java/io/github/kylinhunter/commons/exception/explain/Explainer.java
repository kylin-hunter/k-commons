package io.github.kylinhunter.commons.exception.explain;

import io.github.kylinhunter.commons.exception.common.KThrowable;
import io.github.kylinhunter.commons.exception.info.ErrInfo;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import java.util.function.Function;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-24 02:45
 **/
@Data
@Accessors(chain = true)
public class Explainer<T extends Throwable> {
    private final Class<T> source;
    private Function<T, ExplainResult> explainer;

    /**
     * @author BiJi'an
     * @description
     * @date 2022-01-19 18:59
     **/

    @Data
    public static class ExplainResult {
        private ErrInfo errInfo;
        private Object extra;
        private String msg;

        public ExplainResult(KThrowable KThrowable, String msg) {
            this.errInfo = KThrowable.getErrInfo();
            if (this.errInfo == null) {
                this.errInfo = ErrInfos.UNKNOWN;
            }
            this.extra = KThrowable.getExtra();
            this.msg = msg;
        }

        public ExplainResult(ErrInfo errInfo, String msg) {
            this.errInfo = errInfo;
            if (this.errInfo == null) {
                this.errInfo = ErrInfos.UNKNOWN;
            }
            this.msg = StringUtils.defaultString(msg);
        }

        public ExplainResult(ErrInfo errInfo) {
            this(errInfo, null);
        }

        public String getMsg() {
            if (this.msg != null && this.msg.length() > 0) {
                return this.msg;
            } else {
                return this.errInfo.getDefaultMsg();
            }
        }

    }
}
