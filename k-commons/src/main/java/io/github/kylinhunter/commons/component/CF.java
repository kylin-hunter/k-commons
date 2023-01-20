package io.github.kylinhunter.commons.component;

import java.util.Objects;

import io.github.kylinhunter.commons.exception.embed.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-22 01:48
 **/
public class CF {
    private static final CompManager COMP_MANAGER = new CompManager();

    static {
        init();
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
            COMP_MANAGER.init(pkgs);
            COMP_MANAGER.calComponent();

        } catch (Throwable e) {
            throw new InitException("init component factory error", e);
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
     * @param ct ct
     * @return T
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-12-03 20:08
     */
    public static <T> T get(CT<T> ct) {
        return get(ct.getClazz(), true);
    }

    /**
     * @param compClazz    clazz
     * @param required required
     * @return T
     * @title get
     * @description
     * @author BiJi'an
     * @date 2022-11-08 20:06
     */
    @SuppressWarnings("unchecked")
    public static <T, S extends T> T get(Class<S> compClazz, boolean required) {
        Objects.requireNonNull(compClazz, "clazz can't be null");
        Object manager = COMP_MANAGER.getComponent(compClazz);
        if (manager == null && required) {
            throw new InitException("no component for :" + compClazz);
        }
        return (T) manager;
    }

}
