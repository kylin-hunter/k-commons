package io.github.kylinhunter.commons.component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.reflections.ReflectionUtils;

import com.google.common.collect.Sets;

import io.github.kylinhunter.commons.reflect.ReflectUtil;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/

@RequiredArgsConstructor
class CFieldManager {

    private final CConstructorManager constructorManager;
    private final CMethodManager methodManager;
    private final CompManager compManager;
    private List<CField> allCFields = Lists.newArrayList();

    public void clean() {
        allCFields.clear();
    }

    /**
     * @return void
     * @title calComponents
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:27
     */
    public void calculate() {
        this.clean();
        Set<Class<?>> allCompClasses = this.getAllCompClasses();
        for (Class<?> compClass : allCompClasses) {
            Object o = compManager.get(compClass, true);
            Set<CField> cFields =
                    ReflectionUtils.getAllFields(compClass, (f) -> f.getAnnotation(A.class) != null).stream().map(e ->
                            new CField(e, o)).collect(
                            Collectors.toSet());
            allCFields.addAll(cFields);

        }

        for (CField cfield : allCFields) {
            System.out.println("==>" + cfield.getField().getName());
            Class<?> compClazz = cfield.getField().getType();
            try {
                cfield.getField().setAccessible(true);

                if (List.class.isAssignableFrom(compClazz)) {
                    Class<?> actualTypeArgumentClasses =
                            ReflectUtil.getActualTypeArgumentClasses(cfield.getField().getGenericType(), 0);
                    List<?> value = compManager.getAll(actualTypeArgumentClasses, true);
                    cfield.getField().set(cfield.getCcObject(), value);
                } else {
                    Object value = compManager.get(compClazz, true);
                    cfield.getField().set(cfield.getCcObject(), value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    private Set<Class<?>> getAllCompClasses() {
        Set<Class<?>> allCompClasses = Sets.newHashSet();
        allCompClasses.addAll(constructorManager.getCompClasses());
        allCompClasses.addAll(methodManager.getCompClasses());
        return allCompClasses;
    }

}
