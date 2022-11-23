package io.github.kylinhunter.commons.component;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import io.github.kylinhunter.commons.exception.builtin.InitException;
import io.github.kylinhunter.commons.sys.KConst;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-22 01:48
 **/
public class CF {
    private static final Map<Class<?>, Object> ALL_COMPONENTS = Maps.newHashMap();
    private static final Map<Class<?>, Set<Object>> ALL_I_COMPONENTS = Maps.newHashMap();
    private static final Map<Class<?>, CC> ALL_CCONSTRUCTORS = Maps.newHashMap();
    private static final Map<Type, Set<CC>> ALL_I_CCONSTRUCTORS = Maps.newHashMap();
    private static final Map<Class<?>, Set<Class<?>>> ALL_DEPENDENCIES = Maps.newHashMap();

    static {
        init(KConst.K_BASE_PACKAGE);
    }

    private static void clear() {
        ALL_COMPONENTS.clear();
        ALL_I_COMPONENTS.clear();
        ALL_CCONSTRUCTORS.clear();
        ALL_I_CCONSTRUCTORS.clear();
        ALL_DEPENDENCIES.clear();
    }

    /**
     * @return void
     * @title init
     * @description
     * @author BiJi'an
     * @date 2022-11-08 21:38
     */
    public static void init(String... pkgs) {

        try {

            clear();
            Set<Class<?>> allComponentClazzes = Sets.newHashSet();
            for (String pkg : pkgs) {
                Reflections reflections = new Reflections(pkg, Scanners.TypesAnnotated);
                allComponentClazzes = reflections.getTypesAnnotatedWith(C.class);
            }

            for (Class<?> c : allComponentClazzes) {
                CC cconstructor = new CC(c, c.getConstructors()[0]);
                ALL_CCONSTRUCTORS.put(c, cconstructor);
                Class<?>[] interfaces = c.getInterfaces();
                for (Class<?> anInterface : interfaces) {
                    if (validPkg(anInterface, pkgs)) {
                        ALL_I_CCONSTRUCTORS.compute(anInterface, (k, v) -> {
                            if (v == null) {
                                v = Sets.newHashSet();
                            }
                            v.add(cconstructor);
                            return v;
                        });
                    }

                }
            }

            for (Map.Entry<Class<?>, CC> entry : ALL_CCONSTRUCTORS.entrySet()) {
                CC CC = entry.getValue();
                calDependencies(CC);
            }

            List<CC> sortedCCS = ALL_CCONSTRUCTORS.values().stream()
                    .sorted(Comparator.comparingInt(CC::getDepLevel)).collect(Collectors.toList());

            for (CC cconstructor : sortedCCS) {
                Constructor<?> constructor = cconstructor.getConstructor();
                Class<?> clazz = cconstructor.getClazz();
                int parameterCount = constructor.getParameterCount();
                if (parameterCount <= 0) {

                    try {
                        ALL_COMPONENTS.put(clazz, constructor.newInstance());
                    } catch (Exception e) {
                        throw new InitException("init constructor error:" + clazz.getName(), e);
                    }
                } else {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    Type[] genericParameterTypes = constructor.getGenericParameterTypes();
                    Object[] parameterObj = new Object[parameterCount];
                    for (int i = 0; i < parameterCount; i++) {
                        Class<?> parameterType = parameterTypes[i];
                        Object obj = ALL_COMPONENTS.get(parameterType);
                        if (obj != null) {
                            parameterObj[i] = obj;
                        } else {
                            Type type = genericParameterTypes[i];
                            if (type instanceof ParameterizedType) {
                                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                                for (Type actualTypeArgument : actualTypeArguments) {
                                    Set<CC> allCCS = ALL_I_CCONSTRUCTORS.get(actualTypeArgument);
                                    List<Object> objs = Lists.newArrayList();
                                    if (allCCS != null) {
                                        for (CC tmpCC : allCCS) {
                                            Object tmpObj = ALL_COMPONENTS.get(tmpCC.getClazz());
                                            if (tmpObj != null) {
                                                objs.add(tmpObj);
                                            }
                                        }
                                    }
                                    if (objs.size() > 0) {
                                        parameterObj[i] = objs;
                                    }

                                }
                            }

                            if (parameterObj[i] == null) {
                                throw new InitException(
                                        "no component:" + clazz.getName() + "/" + parameterType.getName());

                            }

                        }
                    }

                    try {
                        ALL_COMPONENTS.put(clazz, constructor.newInstance(parameterObj));
                    } catch (Exception e) {
                        throw new InitException("init constructor error:" + clazz.getName(), e);
                    }
                }

            }

            for (Class<?> componentClazz : allComponentClazzes) {
                Object component = ALL_COMPONENTS.get(componentClazz);
                if (component != null) {
                    Class<?>[] interfaces = componentClazz.getInterfaces();
                    for (Class<?> i : interfaces) {
                        if (validPkg(i, pkgs)) {
                            ALL_I_COMPONENTS.compute(i, (k, v) -> {
                                if (v == null) {
                                    v = Sets.newHashSet();
                                }
                                v.add(component);
                                return v;
                            });

                        }
                    }
                }

            }
            if (ALL_COMPONENTS.size() != allComponentClazzes.size()) {
                throw new InitException("no all  component be initialized ");

            }

        } catch (Throwable e) {
            throw new InitException("init component factory error", e);
        }

    }

    private static boolean validPkg(Class<?> clazz, String[] pkgs) {

        for (String p : pkgs) {
            if (clazz.getName().startsWith(p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param cconstructor cconstructor
     * @return void
     * @title calDependencies
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:06
     */
    private static void calDependencies(CC cconstructor) {

        Class<?> clazz = cconstructor.getClazz();
        calDependencies(clazz, clazz, null);
        cconstructor.setDepLevel(ALL_DEPENDENCIES.get(clazz).size());
    }

    /**
     * @param oriClazz oriClazz
     * @param depClazz depClazz
     * @return void
     * @title calDependencies
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:21
     */
    private static void calDependencies(Class<?> oriClazz, Class<?> depClazz, Type type) {
        Set<CC> allCCS = Sets.newHashSet();
        CC existCC = ALL_CCONSTRUCTORS.get(depClazz);
        if (existCC != null) {
            allCCS.add(existCC);

        } else {
            if (type instanceof ParameterizedType) {
                Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
                for (Type actualTypeArgument : actualTypeArguments) {
                    allCCS = ALL_I_CCONSTRUCTORS.get(actualTypeArgument);
                    if (allCCS != null) {
                        break;
                    }
                }
            }
            if (allCCS == null) {
                throw new InitException("no existCC for :" + depClazz.getName());

            }
        }

        for (CC cconstructor : allCCS) {

            ALL_DEPENDENCIES.compute(oriClazz, (k, v) -> {
                if (v == null) {
                    v = Sets.newHashSet();
                }
                v.add(cconstructor.getClazz());
                return v;
            });
            Constructor<?> constructor = cconstructor.getConstructor();

            if (constructor.getParameterCount() > 0) {
                Type[] genericParameterTypes = constructor.getGenericParameterTypes();

                Class<?>[] parameterTypes = constructor.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    calDependencies(oriClazz, parameterTypes[i], genericParameterTypes[i]);

                }

            }
        }

    }

    /**
     * @param clazz clazz
     * @return T
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:06
     */
    public static <T, S extends T> T get(Class<S> clazz) {
        return get(clazz, true);
    }

    /**
     * @param clazz    clazz
     * @param required required
     * @return T
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:06
     */
    @SuppressWarnings("unchecked")
    public static <T, S extends T> T get(Class<S> clazz, boolean required) {
        Objects.requireNonNull(clazz, "clazz can't be null");
        Object manager = ALL_COMPONENTS.get(clazz);
        if (manager == null && required) {
            throw new InitException("no component for :" + clazz);
        }
        return (T) manager;
    }

}
