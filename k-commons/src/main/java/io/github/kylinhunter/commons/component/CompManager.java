package io.github.kylinhunter.commons.component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;

import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.sys.KConst;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-13 10:52
 **/
public class CompManager {
    protected final Map<Class<?>, List<Object>> allComponents = Maps.newHashMap();
    @Getter
    protected CompTools compTools;
    private final ConstructorCompManager constructorCompManager;
    private final MethodCompManager methodCompManager;

    public CompManager() {
        compTools = new CompTools(KConst.K_BASE_PACKAGE);
        constructorCompManager = new ConstructorCompManager(this);
        methodCompManager = new MethodCompManager(this);
    }

    /**
     * @param pkgs pkgs
     * @return void
     * @title init
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:47
     */
    public void init(String... pkgs) {

        try {
            this.allComponents.clear();
            if (pkgs != null && pkgs.length > 0) {
                compTools.setPkgs(pkgs);
            }
            this.calComponent();
        } catch (Throwable e) {
            throw new InitException("init CompManager error", e);
        }
    }

    /**
     * @return void
     * @title calComponent
     * @description
     * @author BiJi'an
     * @date 2023-02-13 14:56
     */
    public void calComponent() {
        constructorCompManager.calculate();
        methodCompManager.calculate();
    }

    /**
     * @param compClazz compClazz
     * @param required  required
     * @return java.util.List<T>
     * @title getComps
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:22
     */
    @SuppressWarnings("unchecked")
    public <T> List<T> getAll(Class<T> compClazz, boolean required) {
        Objects.requireNonNull(compClazz, "clazz can't be null");
        List<Object> comps = allComponents.get(compClazz);
        if (CollectionUtils.isEmpty(comps) && required) {
            throw new InitException("no component for :" + compClazz);
        }
        return (List<T>) comps;
    }

    /**
     * @param compClazz compClazz
     * @param required  required
     * @return java.util.List<T>
     * @title getComp
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:22
     */
    @SuppressWarnings("unchecked")
    public <T, S extends T> T get(Class<S> compClazz, boolean required) {
        Objects.requireNonNull(compClazz, "clazz can't be null");
        List<Object> comps = allComponents.get(compClazz);
        if (CollectionUtils.isEmpty(comps) && required) {
            throw new InitException("no component for :" + compClazz);
        }
        if (comps != null && comps.size() > 0) {
            return (T) comps.get(0);
        }
        return null;
    }

    /**
     * @param clazz clazz
     * @return void void
     * @title register
     * @description
     * @author BiJi'an
     * @date 2023-02-04 20:29
     */
    public void register(Class<?> clazz, boolean primary, Object obj) {
        try {
            allComponents.compute(clazz, (k, v) -> {
                if (v == null) {
                    v = Lists.newArrayList();
                }
                if (!v.contains(obj)) {
                    if (primary) {
                        v.add(0, obj);
                    } else {
                        v.add(obj);

                    }
                }
                return v;
            });
            Set<Class<?>> allInterfaces = compTools.getAllInterface(clazz);
            if (allInterfaces != null && allInterfaces.size() > 0) {
                for (Class<?> iterfaceClass : allInterfaces) {
                    allComponents.compute(iterfaceClass, (k, v) -> {
                        if (v == null) {
                            v = Lists.newArrayList();
                        }
                        if (!v.contains(obj)) {
                            if (primary) {
                                v.add(0, obj);
                            } else {
                                v.add(obj);

                            }
                        }
                        return v;
                    });
                }

            }

        } catch (Exception e) {
            throw new InitException("init components error:" + clazz.getName(), e);
        }
    }

    /**
     * @param compClazzes compClazzes
     * @return void
     * @title check
     * @description
     * @author BiJi'an
     * @date 2023-02-12 22:57
     */
    public void check(Set<Class<?>> compClazzes) {

        for (Class<?> compClazz : compClazzes) {
            if (this.get(compClazz, false) == null) {
                throw new InitException("no    component be initialized " + compClazz.getName());
            }
        }

    }
}
