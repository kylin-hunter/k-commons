package io.github.kylinhunter.commons.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.reflect.BeanCreator;
import io.github.kylinhunter.commons.reflect.ReflectUtil;
import io.github.kylinhunter.commons.sys.KConst;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-25 23:17
 **/

class CompManager {
    protected final Map<Class<?>, Object> allComponents = Maps.newHashMap();
    protected final Map<Class<?>, Set<Object>> allInterfaceComponents = Maps.newHashMap();
    @Getter
    protected final CompTools compTools = new CompTools(KConst.K_BASE_PACKAGE);
    protected final CompConstructorManager compConstructorManager = new CompConstructorManager(compTools);
    protected MethodCompManager methodCompManager;

    /**
     * @param pkgs pkgs
     * @return void
     * @title init
     * @description
     * @author BiJi'an
     * @date 2023-01-21 00:06
     */
    public void init(String... pkgs) {
        compConstructorManager.clear();
        allComponents.clear();
        allInterfaceComponents.clear();
        if (pkgs != null && pkgs.length > 0) {
            compTools.setPkgs(pkgs);
        }
        methodCompManager = new MethodCompManager(this);
    }

    /**
     * @return void
     * @title calComponents
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:27
     */
    public void calComponent() {
        List<CompConstructor> compConstructors = compConstructorManager.calCompConstructor();
        for (CompConstructor compConstructor : compConstructors) {
            calComponent(compConstructor);
        }

        Set<Class<?>> compClazzes = compConstructorManager.getCompClazzes();
        for (Class<?> compClazz : compClazzes) {
            Object component = allComponents.get(compClazz);
            if (component != null) {
                Set<Class<?>> interfaces = compTools.getAllInterface(compClazz);
                for (Class<?> i : interfaces) {
                    allInterfaceComponents.compute(i, (k, v) -> {
                        if (v == null) {
                            v = Sets.newHashSet();
                        }
                        v.add(component);
                        return v;
                    });

                }
            }

        }
        if (allComponents.size() != compClazzes.size()) {
            throw new InitException("no all  component be initialized ");

        }
        methodCompManager.calComponent();
    }

    /**
     * @param cconstructor cconstructor
     * @return void
     * @title calComponent
     * @description
     * @author BiJi'an
     * @date 2023-01-21 00:37
     */
    public void calComponent(CompConstructor cconstructor) {
        Constructor<?> constructor = cconstructor.getConstructor();
        Class<?> clazz = cconstructor.getClazz();
        int parameterCount = constructor.getParameterCount();
        if (parameterCount <= 0) {
            allComponents.put(clazz, BeanCreator.createBean(constructor));
        } else {

            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Type[] genericParameterTypes = constructor.getGenericParameterTypes();
            Object[] parameterObj = new Object[parameterCount];
            for (int i = 0; i < parameterCount; i++) {
                Class<?> parameterType = parameterTypes[i];
                Object obj = allComponents.get(parameterType);
                if (obj != null) {
                    parameterObj[i] = obj;
                } else {
                    ParameterizedType parameterizedType = ReflectUtil.toParameterizedType(genericParameterTypes[i]);
                    if (parameterizedType != null) {
                        Class<?> rawTypeClazz = ReflectUtil.getRawTypeClass(parameterizedType);
                        if (rawTypeClazz != null) {
                            if (compTools.isValid(rawTypeClazz)) {
                                CompConstructor compConstructor = compConstructorManager.getByInterface(rawTypeClazz);
                                if (compConstructor != null) {
                                    parameterObj[i] = allComponents.get(compConstructor.getClazz());
                                }
                            } else if (List.class.isAssignableFrom(rawTypeClazz)) {
                                Class<?> argClass = ReflectUtil.getActualTypeArgumentClasses(parameterizedType, 0);
                                Set<CompConstructor> constructors = compConstructorManager.getAllByInterface(argClass);

                                List<Object> objs = constructors.stream()
                                        .map(c -> allComponents.get(c.getClazz()))
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toList());

                                if (objs.size() > 0) {
                                    parameterObj[i] = objs;
                                }

                            }
                        }
                    }

                    if (parameterObj[i] == null) {
                        throw new InitException("no component:" + clazz.getName() + "/" + parameterType.getName());

                    }

                }
            }

            try {
                this.allComponents.put(clazz, constructor.newInstance(parameterObj));
            } catch (Exception e) {
                throw new InitException("init constructor error:" + clazz.getName(), e);
            }
        }
    }

    /**
     * @param clazz clazz
     * @return java.lang.Object
     * @title getComponent
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:22
     */
    public Object getComponent(Class<?> clazz) {
        return this.allComponents.get(clazz);
    }

    /**
     * @param clazz clazz
     * @return java.lang.Object
     * @title getComponent
     * @description
     * @author BiJi'an
     * @date 2023-01-20 00:22
     */
    public Set<Object> getComponents(Class<?> clazz) {
        Object comp = this.allComponents.get(clazz);
        if (comp == null) {
            return this.allInterfaceComponents.get(clazz);
        } else {
            Set<Object> comps = this.allInterfaceComponents.get(clazz);
            if (comps != null) {
                Set<Object> sets = Sets.newHashSet();
                sets.addAll(comps);
                sets.add(comp);
                return sets;

            } else {
                return Sets.newHashSet(comp);
            }
        }
    }

    /**
     * @param clazz clazz
     * @return void void
     * @title register
     * @description
     * @author BiJi'an
     * @date 2023-02-04 20:29
     */
    public void registerComponent(Class<?> clazz, Object obj) {
        try {
            if (clazz.isInterface()) {
                allInterfaceComponents.compute(clazz, (k, v) -> {
                    if (v == null) {
                        v = Sets.newHashSet();
                    }
                    v.add(obj);
                    return v;
                });
            } else {
                allComponents.put(clazz, obj);
            }
        } catch (Exception e) {
            throw new InitException("init constructor error:" + clazz.getName(), e);
        }
    }
}
