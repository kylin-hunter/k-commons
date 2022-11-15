package com.kylinhunter.plat.commons.exception.info;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/1/1
 **/
@Data
public class ErrInfoClassify {
    private final int code;
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private int index;
    public static final int MAX_CAPACITY = 10000;

    public int next() {
        index++;
        if (index == MAX_CAPACITY) {
            throw new RuntimeException("MAX_CAPACITY:" + MAX_CAPACITY);
        }
        return code * MAX_CAPACITY + index;
    }

}
