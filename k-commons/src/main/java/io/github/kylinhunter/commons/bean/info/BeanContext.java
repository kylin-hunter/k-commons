package io.github.kylinhunter.commons.bean.info;

import java.util.Map;
import java.util.Set;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 23:22
 **/
public class BeanContext {

    private final Map<Integer, Set<Class<?>>> duplicates = MapUtils.newHashMap();

    /**
     * @param level level
     * @param clazz clazz
     * @return boolean
     * @title accept
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:40
     */
    public boolean accept(int level, Class<?> clazz) {
        for (int i = 1; i < level; i++) {
            Set<Class<?>> classes = duplicates.computeIfAbsent(i, (k) -> SetUtils.newHashSet());
            if (classes.contains(clazz)) {
                return false;
            }
        }
        Set<Class<?>> classes = duplicates.computeIfAbsent(level, (k) -> SetUtils.newHashSet());
        classes.add(clazz);
        return true;

    }
}
