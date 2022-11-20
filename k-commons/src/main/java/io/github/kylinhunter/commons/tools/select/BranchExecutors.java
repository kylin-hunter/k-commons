package io.github.kylinhunter.commons.tools.select;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-06 17:05
 **/
public class BranchExecutors {

    /**
     * @param p p
     * @return io.github.kylinhunter.commons.tools.select.BranchExecutor<java.lang.reflect.Field,
            * io.github.kylinhunter.generator.core.configuration.bean.EntityField>
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
