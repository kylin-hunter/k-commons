package io.github.kylinhunter.commons.bean.info;

import org.apache.commons.lang3.ClassUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 10:14
 **/
public enum PropType {
    PRIMITIVE, PRIMITIVE_WRAP, BASIC, CUSTOM;

    PropType valueof(Class<?> clazz) {

        if (clazz.isPrimitive()) {
            return PRIMITIVE;
        } else if (ClassUtils.isPrimitiveWrapper(clazz)) {
            return PRIMITIVE_WRAP;
        } else {
            String name = clazz.getName();
            if (name.startsWith("java ")) {
                if (name.startsWith("java ") && name.startsWith("sun")) {
                    return CUSTOM;
                } else {
                    return null;
                }
            }

        }
        return null;
    }
}
