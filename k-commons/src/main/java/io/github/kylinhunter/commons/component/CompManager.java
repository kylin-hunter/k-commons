package io.github.kylinhunter.commons.component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.sys.KConst;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-11 10:52
 **/
public class CompManager {
    protected final Map<Class<?>, CObjects> allComponents = Maps.newHashMap();
    @Getter
    protected CompTools compTools;
    protected final ConstructorCompManager constructorCompManager;
    protected final MethodCompManager methodCompManager;
    private final CFieldCompSetter cfieldCompSetter;
    private final CAfterMethodCalculator cafterMethodCalculator;

    public CompManager() {
        compTools = new CompTools(KConst.K_BASE_PACKAGE);
        constructorCompManager = new ConstructorCompManager(this);
        methodCompManager = new MethodCompManager(this);
        cfieldCompSetter = new CFieldCompSetter(this);
        cafterMethodCalculator = new CAfterMethodCalculator(this);
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
     * @date 2023-02-11 14:56
     */
    public void calComponent() {
        constructorCompManager.calculate();
        methodCompManager.calculate();
        cfieldCompSetter.calculate();
        cafterMethodCalculator.calculate();
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
        CObjects cobjects = allComponents.get(compClazz);
        if (cobjects != null && !cobjects.isEmpty()) {
            return (List<T>) cobjects.getObjects();
        }
        if (required) {
            throw new InitException("no component for :" + compClazz);
        }
        return null;
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
        CObjects cobjects = allComponents.get(compClazz);
        if (cobjects != null && !cobjects.isEmpty()) {
            return (T) cobjects.getObject();
        }
        if (required) {
            throw new InitException("no component for :" + compClazz);
        }
        return null;
    }

    /**
     * @param clazz        clazz
     * @param cconstructor cconstructor
     * @param obj          obj
     * @return void
     * @title register
     * @description
     * @author BiJi'an
     * @date 2023-02-12 11:25
     */
    @SuppressWarnings("UnusedReturnValue")
    public List<CObjects> register(Class<?> clazz, CConstructor cconstructor, Object obj) {
        CObject cobject = new CObject(cconstructor, obj);
        return register(clazz, cobject);
    }

    /**
     * @param clazz   clazz
     * @param cmethod cmethod
     * @param obj     obj
     * @return void
     * @title register
     * @description
     * @author BiJi'an
     * @date 2023-02-12 11:25
     */
    @SuppressWarnings("UnusedReturnValue")
    public List<CObjects> register(Class<?> clazz, CMethod cmethod, Object obj) {
        CObject cobject = new CObject(cmethod, obj);
        return register(clazz, cobject);
    }

    /**
     * @param clazz clazz
     * @param obj   obj
     * @return void
     * @title register
     * @description
     * @author BiJi'an
     * @date 2023-02-12 14:03
     */
    @SuppressWarnings("UnusedReturnValue")
    public List<CObjects> register(Class<?> clazz, Object obj) {
        CObject cobject = new CObject(true, 0, obj);
        return register(clazz, cobject);
    }

    /**
     * @param clazz clazz
     * @return void void
     * @title register
     * @description
     * @author BiJi'an
     * @date 2023-02-04 20:29
     */
    private List<CObjects> register(Class<?> clazz, CObject ccbject) {
        try {

            List<CObjects> allAffectedCObjects = Lists.newArrayList();
            CObjects affectedCObject = allComponents.compute(clazz, (k, cobjects) -> {
                if (cobjects == null) {
                    cobjects = new CObjects();
                }
                cobjects.add(ccbject);
                return cobjects;
            });
            allAffectedCObjects.add(affectedCObject);
            Set<Class<?>> allInterfaces = compTools.getAllInterface(clazz);
            if (allInterfaces != null && allInterfaces.size() > 0) {
                for (Class<?> iterfaceClass : allInterfaces) {
                    CObjects affectedCObjects = allComponents.compute(iterfaceClass, (k, cobjects) -> {
                        if (cobjects == null) {
                            cobjects = new CObjects();
                        }
                        cobjects.add(ccbject);
                        return cobjects;
                    });
                    allAffectedCObjects.add(affectedCObjects);

                }

            }
            return allAffectedCObjects;

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
