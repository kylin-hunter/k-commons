package io.github.kylinhunter.commons.bean.info;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:08
 **/
@Data
public class ExPropertyDescriptor {
    private PropertyDescriptor propertyDescriptor;
    private boolean canReadWrite;
    private ExPropType exPropType = ExPropType.ORIGINAL;
    private Class<?>[] actualClazzes;

    public ExPropertyDescriptor(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
        this.canReadWrite =
                propertyDescriptor.getWriteMethod() != null && this.propertyDescriptor.getReadMethod() != null;
    }

    /**
     * @return java.lang.Class<?>
     * @title getPropActualClazz
     * @description
     * @author BiJi'an
     * @date 2023-03-19 14:34
     */
    public Class<?> getPropActualClazz() {
        if (actualClazzes != null && actualClazzes.length > 0) {
            return actualClazzes[0];
        }
        return null;
    }

    /**
     * @return java.lang.String
     * @title getName
     * @description
     * @author BiJi'an
     * @date 2023-04-01 10:46
     */
    public String getName() {
        return propertyDescriptor.getName();
    }

    /**
     * @return java.lang.Class<?>
     * @title getPropertyType
     * @description
     * @author BiJi'an
     * @date 2023-04-01 10:51
     */
    public Class<?> getPropertyType() {
        return propertyDescriptor.getPropertyType();
    }

    /**
     * @return java.lang.reflect.Method
     * @title getReadMethod
     * @description
     * @author BiJi'an
     * @date 2023-04-01 10:55
     */
    public Method getReadMethod() {
        return propertyDescriptor.getReadMethod();
    }

    /**
     * @return java.lang.reflect.Method
     * @title getWriteMethod
     * @description
     * @author BiJi'an
     * @date 2023-04-01 10:55
     */
    public Method getWriteMethod() {
        return propertyDescriptor.getWriteMethod();
    }

}
