package io.github.kylinhunter.commons.branch;

/**
 * @author BiJi'an
 * @description
 * @date 2022-01-06 17:05
 **/
public class BranchExecutors {

    /**
     * @param p      p
     * @param tClass
     * @return io.github.kylinhunter.commons.select.BranchExecutor<P, T>
     * @title use
     * @description
     * @author BiJi'an
     * @date 2022-11-21 02:40
     */
    public static <P, T> BranchExecutor<P, T> use(P p, Class<T> tClass) {
        return new BranchExecutor<>(p);
    }

    /**
     * @param p p
     * @return io.github.kylinhunter.commons.select.BranchExecutor<P, T>
     * @title use
     * @description
     * @author BiJi'an
     * @date 2022-11-21 02:40
     */
    public static <P, T> BranchExecutor<P, T> use(P p) {
        return new BranchExecutor<>(p);
    }

}
