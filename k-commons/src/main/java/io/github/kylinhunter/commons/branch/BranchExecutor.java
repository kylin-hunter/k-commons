package io.github.kylinhunter.commons.branch;

import java.util.function.Function;
import java.util.function.Predicate;
import lombok.ToString;

/**
 * @author BiJi'an
 * @description
 * @date 2022/01/01
 **/
@ToString(of = "selected")
public class BranchExecutor<P, T> {
    private boolean selected = false;
    private Function<P, T> factory;

    private final P param;

    public BranchExecutor(P param) {
        this.param = param;
    }

    /**
     * @param param param
     * @return io.github.kylinhunter.commons.tools.select.BranchExecutor<P, T>
     * @title
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:51 下午
     */
    public static <P, T> BranchExecutor<P, T> param(P param) {
        return new BranchExecutor<>(param);
    }

    /**
     * @param branch branch
     * @return io.github.kylinhunter.commons.tools.select.BranchExecutor<P, T>
     * @title
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:51 下午
     */
    public BranchExecutor<P, T> test(Branch<P, T> branch) {
        if (!selected) {
            boolean pass = branch.tester().test(param);
            if (pass) {
                selected = true;
                factory = branch.factory();
            }
        }
        return this;
    }

    /**
     * @param supplier supplier
     * @return T
     * @title
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:52 下午
     */
    public T others(Function<P, T> supplier) {
        return selected ? this.factory.apply(param) : supplier.apply(param);
    }

    /**
     * @param tester tester
     * @return io.github.kylinhunter.commons.select.BranchBuilder<P, T>
     * @title predicate
     * @description
     * @author BiJi'an
     * @date 2022-11-21 02:39
     */
    public BranchBuilder<P, T> predicate(Predicate<P> tester) {

        return factory -> Branch.of(tester, factory);
    }

}
