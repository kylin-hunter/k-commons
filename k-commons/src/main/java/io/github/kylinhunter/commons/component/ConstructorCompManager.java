package io.github.kylinhunter.commons.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.List;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.BeanCreator;
import io.github.kylinhunter.commons.reflect.GenericTypeUtils;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/

@RequiredArgsConstructor
class ConstructorCompManager {

    private final CompManager compManager;
    protected final CConstructorManager constructorManager;

    public ConstructorCompManager(CompManager compManager) {
        this.compManager = compManager;
        this.constructorManager = new CConstructorManager(compManager);
    }

    /**
     * @return void
     * @title calComponents
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:27
     */
    public void calculate() {
        constructorManager.calculate();
        List<CConstructor> cconstructors = constructorManager.getConstructors();
        for (CConstructor cconstructor : cconstructors) {
            calculate(cconstructor);
        }
        compManager.check(constructorManager.getCompClasses());
    }

    /**
     * @param cconstructor cconstructor
     * @return void
     * @title calComponent
     * @description
     * @author BiJi'an
     * @date 2023-01-21 00:37
     */
    public void calculate(CConstructor cconstructor) {
        Constructor<?> constructor = cconstructor.getConstructor();
        Class<?> clazz = cconstructor.getClazz();
        int parameterCount = constructor.getParameterCount();
        if (parameterCount <= 0) {
            compManager.register(clazz, cconstructor, BeanCreator.createBean(constructor));
        } else {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Type[] genericParameterTypes = constructor.getGenericParameterTypes();
            Object[] parameterObj = new Object[parameterCount];
            for (int i = 0; i < parameterCount; i++) {
                Class<?> curParametorClass = parameterTypes[i];
                Object obj = compManager.get(curParametorClass, false);
                if (obj != null) {
                    parameterObj[i] = obj;
                } else {
                    if (List.class.isAssignableFrom(curParametorClass)) {
                        Type type = genericParameterTypes[i];
                        Class<?> argClass = GenericTypeUtils.getActualTypeArgument(type, 0);
                        List<?> comps = compManager.getAll(argClass, false);
                        if (comps.size() > 0) {
                            parameterObj[i] = comps;
                        }
                    }
                }
                if (parameterObj[i] == null) {
                    throw new InitException("no component:" + clazz.getName());
                }
            }
            this.compManager.register(clazz, cconstructor, BeanCreator.createBean(constructor, parameterObj));

        }
    }

}
