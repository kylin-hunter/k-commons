package io.github.kylinhunter.commons.component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.reflections.ReflectionUtils;

import com.google.common.collect.Sets;

import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/

@RequiredArgsConstructor
@Slf4j
class CFieldCompSetter {

    private CConstructorManager constructorManager;
    private CMethodManager methodManager;
    private CompManager compManager;

    public CFieldCompSetter(CompManager compManager) {
        this.compManager = compManager;
        this.constructorManager = compManager.constructorCompManager.constructorManager;
        this.methodManager = compManager.methodCompManager.methodManager;
    }

    /**
     * @return void
     * @title calComponents
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:27
     */
    public void calculate() {

        List<CField> allCFields = this.getAllCFields();

        for (CField cfield : allCFields) {
            Class<?> compClazz = cfield.getType();
            try {
                cfield.getField().setAccessible(true);
                if (List.class.isAssignableFrom(compClazz)) {
                    Class<?> actualTypeArgumentClasses =
                            GenericTypeUtils.getActualTypeArgument(cfield.getGenericType(), 0);
                    List<?> value = compManager.getAll(actualTypeArgumentClasses, true);
                    cfield.getField().set(cfield.getCompObject(), value);
                    log.info("set {}.{}", cfield.getCompObject().getClass().getSimpleName(),
                            value.getClass().getSimpleName());
                } else {
                    Object value = compManager.get(compClazz, true);
                    cfield.getField().set(cfield.getCompObject(), value);
                    log.info("set {}.{}", cfield.getCompObject().getClass().getSimpleName(),
                            value.getClass().getSimpleName());
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * @return java.util.Set<java.lang.Class < ?>>
     * @title getAllCompClasses
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:42
     */
    @SuppressWarnings("unchecked")
    private List<CField> getAllCFields() {
        List<CField> allCFields = Lists.newArrayList();
        Set<Class<?>> allCompClasses = Sets.newHashSet(constructorManager.getCompClasses());
        allCompClasses.addAll(methodManager.getCompClasses());
        for (Class<?> compClass : allCompClasses) {
            Object o = compManager.get(compClass, true);
            Set<Field> fields = ReflectionUtils.getAllFields(compClass, (f) -> f.getAnnotation(A.class) != null);
            if (!CollectionUtils.isEmpty(fields)) {
                Set<CField> cFields = fields.stream().map(e -> new CField(e, o)).collect(Collectors.toSet());
                allCFields.addAll(cFields);
            }
        }
        return allCFields;
    }

}