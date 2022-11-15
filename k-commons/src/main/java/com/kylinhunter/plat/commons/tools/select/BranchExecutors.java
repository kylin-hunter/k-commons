package com.kylinhunter.plat.commons.tools.select;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-06 17:05
 **/
public class BranchExecutors {

    /**
     * @param p p
     * @return com.kylinhunter.plat.commons.tools.select.BranchExecutor<java.lang.reflect.Field,
            * com.kylinhunter.plat.generator.core.configuration.bean.EntityField>
     * @title use
     * @description
     * @author BiJi'an
     * @date 2022-05-28 02:12
     */
    public static <P, T> BranchExecutor<P, T> use(P p,  Class<T> tClass) {
        return new BranchExecutor<>(p);
    }

    public static <P, T> BranchExecutor<P, T> use(P p) {
        return new BranchExecutor<>(p);
    }

}
