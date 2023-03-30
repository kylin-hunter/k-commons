package io.github.kylinhunter.commons.bean.info;

import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;

import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import io.github.kylinhunter.commons.reflect.bean.ActualType;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 23:22
 **/
public class BeanInfoHelper {

    private static final Set<Class<?>> skipClazzes = SetUtils.newHashSet();

    static {
        skipClazzes.add(Class.class);
    }

    /**
     * @param pd pd
     * @return boolean
     * @title access
     * @description
     * @author BiJi'an
     * @date 2023-03-19 15:03
     */
    private static boolean canReadWrite(java.beans.PropertyDescriptor pd) {
        if (pd.getWriteMethod() != null && pd.getReadMethod() != null) {
            Class<?> propertyType = pd.getPropertyType();
            return !skipClazzes.contains(propertyType);
        }
        return false;
    }

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
        boolean canReadWrite = canReadWrite(pd);
        exPropertyDescriptor.setCanReadWrite(canReadWrite);
        if (!canReadWrite) {
            return exPropertyDescriptor;
        }
        if (ClassUtils.isPrimitiveOrWrapper(propertyType)) {
            exPropertyDescriptor.setExPropType(ExPropType.PRIMITIVE_OR_WRAPPER);

        } else if (propertyType.isArray()) {
            exPropertyDescriptor.setExPropType(ExPropType.ARRAY);
            exPropertyDescriptor.setActualClazzes(new Class[] {propertyType.getComponentType()});
        } else if (List.class.isAssignableFrom(propertyType)) {
            ActualType actualType = GenericTypeUtils.getMethodReturnActualType(pd.getReadMethod());
            if (actualType != null && actualType.getType(0) != null) {
                exPropertyDescriptor.setExPropType(ExPropType.LIST);
                exPropertyDescriptor.setActualClazzes(actualType.getTypes());
            }
        } else {
            String packageName = ClassUtils.getPackageName(propertyType);
            if (!packageName.startsWith("java.") && !packageName.startsWith("com.sun")) {
                exPropertyDescriptor.setExPropType(ExPropType.CUSTOM);
            }
        }
        return exPropertyDescriptor;

    }

}
