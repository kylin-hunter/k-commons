package io.github.kylinhunter.commons.bean.copy;

import io.github.kylinhunter.commons.bean.copy.convertor.ClassConvertor;
import io.github.kylinhunter.commons.bean.copy.convertor.Direction;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Data
public abstract class AbstractClassConvertor<S, T> implements ClassConvertor<S, T> {
    protected final Direction direction;

    @SuppressWarnings("unchecked")
    @Override
    public void convert(S source, T target) {
        if (direction == Direction.FORWARD) {
            this.forword(source, target);
        } else {
            this.backward((T) source, (S) target);
        }
    }

    /**
     * @param source source
     * @param target target
     * @return void
     * @title forword
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:06
     */
    public abstract void forword(S source, T target);

    /**
     * @param source source
     * @param target target
     * @return void
     * @title backward
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:06
     */
    public abstract void backward(T source, S target);

}
