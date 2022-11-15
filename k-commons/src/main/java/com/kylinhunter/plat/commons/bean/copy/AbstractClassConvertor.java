package com.kylinhunter.plat.commons.bean.copy;

import com.kylinhunter.plat.commons.bean.copy.convertor.ClassCopyConvertor;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Data
public abstract class AbstractClassConvertor<S, T> implements ClassCopyConvertor<S, T> {
    protected final boolean reverse;

    @SuppressWarnings("unchecked")
    @Override
    public void convert(S source, T target) {
        if (reverse) {
            this.backward((T) source, (S) target);
        } else {
            this.forword(source, target);
        }
    }

    public abstract void forword(S source, T target);

    public abstract void backward(T source, S target);

}
