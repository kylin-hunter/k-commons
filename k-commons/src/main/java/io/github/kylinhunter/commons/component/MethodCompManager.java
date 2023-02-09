package io.github.kylinhunter.commons.component;

import java.lang.reflect.Method;
import java.util.Set;

import org.reflections.ReflectionUtils;

import io.github.kylinhunter.commons.reflect.BeanCreator;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/

@RequiredArgsConstructor
class MethodCompManager {
    private CompManager compManager;
    private CompTools compTools;

    public MethodCompManager(CompManager compManager) {
        this.compManager = compManager;
        this.compTools = compManager.compTools;
    }

    /**
     * @return void
     * @title calComponents
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:27
     */
    public void calComponent() {
        Set<Class<?>> clazzes = compTools.calAllCompMethodClazzes();

        for (Class<?> clazz : clazzes) {
            Object bean = BeanCreator.createBean(clazz);
            compManager.registerComponent(clazz, bean);

            Set<Method> methods = ReflectionUtils.getAllMethods(clazz);
            for (Method method : methods) {
                C c = method.getAnnotation(C.class);
                if (c != null) {
                    Object invokeObj = ReflectionUtils.invoke(method, bean);
                    Class<?> returnClazz = invokeObj.getClass();

                    compManager.registerComponent(returnClazz, invokeObj);

                    Set<Class<?>> allInterfaces = compTools.getAllInterface(returnClazz);
                    for (Class<?> i : allInterfaces) {
                        compManager.registerComponent(i, invokeObj);
                    }

                }
            }

        }

    }
}
