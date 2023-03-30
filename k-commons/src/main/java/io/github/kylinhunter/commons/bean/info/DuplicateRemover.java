package io.github.kylinhunter.commons.bean.info;

import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-01 19:13
 **/
public class DuplicateRemover {
    private final Map<Integer, Set<Class<?>>> duplicates = MapUtils.newHashMap();

    /**
     * @param curLevel             curLevel
     * @param exPropertyDescriptor exPropertyDescriptor
     * @return boolean
     * @title duplicate
     * @description
     * @author BiJi'an
     * @date 2023-04-01 10:48
     */
    public boolean duplicate(int curLevel, ExPropertyDescriptor exPropertyDescriptor) {
        PropertyDescriptor propertyDescriptor = exPropertyDescriptor.getPropertyDescriptor();
        Class<?> propertyType = propertyDescriptor.getPropertyType();
        if (exPropertyDescriptor.getExPropType() == ExPropType.CUSTOM) {
            for (int level = 1; level < curLevel; level++) {
                if (this.contains(level, propertyType)) {
                    return true;
                }
            }
            this.put(curLevel, propertyType);
        }
        return false;
    }

    /**
     * @param level level
     * @param clazz clazz
     * @return boolean
     * @title contains
     * @description
     * @author BiJi'an
     * @date 2023-03-19 14:32
     */
    private boolean contains(int level, Class<?> clazz) {
        Set<Class<?>> classes = duplicates.computeIfAbsent(level, (k) -> SetUtils.newHashSet());
        return classes.contains(clazz);
    }

    /**
     * @param level level
     * @param clazz clazz
     * @return void
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-03-19 14:32
     */
    private void put(int level, Class<?> clazz) {
        Set<Class<?>> classes = duplicates.computeIfAbsent(level, (k) -> SetUtils.newHashSet());
        classes.add(clazz);
    }

}
