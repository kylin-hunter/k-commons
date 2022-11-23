package io.github.kylinhunter.commons.exception.info;

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
    private int code;
    private String defaultMsg;

    public ErrInfo(int code) {
        this.code = code;
    }

    /**
     * @return int
     * @title next
     * @description
     * @author BiJi'an
     * @date 2022-11-24 01:37
     */
    protected int next() {
        return code + 1;
    }
}
