package io.github.kylinhunter.commons.component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.reflections.ReflectionUtils;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.SetUtils;;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
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

            Field field = cfield.getField();
            Object compObject = cfield.getCompObject();
            Object value;
            if (List.class.isAssignableFrom(compClazz)) {
                Class<?> actualTypeArgumentClasses = GenericTypeUtils.getActualTypeArgument(cfield.getGenericType(), 0);
                value = compManager.getAll(actualTypeArgumentClasses, true);
                ReflectUtils.set(compObject, field, value);
            } else {
                value = compManager.get(compClazz, false);
                if (value == null) {
                    throw new InitException(compObject.getClass().getName() + " can't set " + compClazz.getName());
                }
            }
            ReflectUtils.set(compObject, field, value);

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
        List<CField> allCFields = ListUtils.newArrayList();
        Set<Class<?>> allCompClasses = SetUtils.newHashSet(constructorManager.getCompClasses());
        allCompClasses.addAll(methodManager.getCompClasses());
        for (Class<?> compClass : allCompClasses) {
            Object o = compManager.get(compClass, true);
            Set<Field> fields = ReflectionUtils.getAllFields(compClass, (f) -> f.getAnnotation(CSet.class) != null);
            if (!CollectionUtils.isEmpty(fields)) {
                Set<CField> cFields = fields.stream().map(e -> new CField(e, o)).collect(Collectors.toSet());
                allCFields.addAll(cFields);
            }
        }
        return allCFields;
    }

}