package io.github.kylinhunter.commons.bean.copy;

import java.beans.PropertyDescriptor;

import io.github.kylinhunter.commons.bean.copy.convertor.ConvertExcetion;
import io.github.kylinhunter.commons.bean.copy.convertor.Direction;
import io.github.kylinhunter.commons.bean.copy.convertor.FieldConvertor;
import io.github.kylinhunter.commons.exception.embed.InternalException;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-01 19:09
 **/
@Data
public abstract class AbstractFieldConvertor implements FieldConvertor {

    protected final Direction direction;
    protected final PropertyDescriptor sourcePD;
    protected final PropertyDescriptor targetPD;

    /**
     * @param source source
     * @return java.lang.Object
     * @title read
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:03
     */
    @SuppressWarnings("unchecked")
    public <T> T read(Object source) {
        try {
            return (T) sourcePD.getReadMethod().invoke(source);
        } catch (Exception e) {
            throw new InternalException("read error", e);
        }

    }

    /**
     * @param target target
     * @param value  value
     * @return void
     * @title write
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:06
     */
    public void write(Object target, Object value) {
        try {
            targetPD.getWriteMethod().invoke(target, value);
        } catch (Exception e) {
            throw new InternalException("write error", e);
        }
    }

    @Override
    public void convert(Object source, Object target) throws ConvertExcetion {
        try {
            if (direction == Direction.FORWARD) {
                this.forword(source, target);
            } else {
                this.backward(source, target);
            }
        } catch (ConvertExcetion convertExcetion) {
            throw convertExcetion;
        } catch (Exception e) {
            throw new ConvertExcetion("convert error", e);
        }
    }

    /**
     * @param source source
     * @param target target
     * @return void
     * @title forword
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:14
     */
    public abstract void forword(Object source, Object target) throws ConvertExcetion;

    /**
     * @param source source
     * @param target target
     * @return void
     * @title backward
     * @description
     * @author BiJi'an
     * @date 2022-11-19 01:14
     */
    public abstract void backward(Object source, Object target) throws ConvertExcetion;

}
