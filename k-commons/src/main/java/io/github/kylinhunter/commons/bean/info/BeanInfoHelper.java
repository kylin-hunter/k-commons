package io.github.kylinhunter.commons.bean.info;

import java.beans.PropertyDescriptor;
import java.util.List;

import org.apache.commons.lang3.ClassUtils;

import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import io.github.kylinhunter.commons.reflect.bean.ActualType;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 23:22
 **/
public class BeanInfoHelper {

    /**
     * @param pd pd
     * @return boolean
     * @title accept
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:40
     */
    public static ExPropertyDescriptor getExFieldDescriptor(PropertyDescriptor pd) {
        ExPropertyDescriptor exPropertyDescriptor = new ExPropertyDescriptor(pd);
        Class<?> propertyType = pd.getPropertyType();

        if (ClassUtils.isPrimitiveOrWrapper(propertyType)) {
            exPropertyDescriptor.setExPropType(ExPropType.PRIMITIVE_OR_WRAPPER);
        } else if (propertyType == String.class) {
            exPropertyDescriptor.setExPropType(ExPropType.STRING);
        } else if (propertyType.isArray()) {
            exPropertyDescriptor.setExPropType(ExPropType.ARRAY);
            exPropertyDescriptor.setGenericActualClazzes(new Class[] {propertyType.getComponentType()});
        } else if (List.class.isAssignableFrom(propertyType)) {
            exPropertyDescriptor.setExPropType(ExPropType.LIST);
            ActualType actualType = GenericTypeUtils.getMethodReturnActualType(pd.getReadMethod());
            if (actualType != null && actualType.getType(0) != null) {
                exPropertyDescriptor.setGenericActualClazzes(actualType.getTypes());
            }
        } else {
            String packageName = ClassUtils.getPackageName(propertyType);
            if (!packageName.startsWith("java.") && !packageName.startsWith("com.sun")) {
                exPropertyDescriptor.setExPropType(ExPropType.NON_JDK_TYPE);
            }
        }
        return exPropertyDescriptor;

    }

}
