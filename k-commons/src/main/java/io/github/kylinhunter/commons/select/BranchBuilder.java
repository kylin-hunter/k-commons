package io.github.kylinhunter.commons.select;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author BiJi'an
 * @description
 * @date 2022/01/01
 **/
public interface BranchBuilder<P, T> {

    /**
     * @param factory factory
     * @return io.github.kylinhunter.commons.tools.select.Branch<P, T>
     * @title
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:49 下午
     */
    Branch<P, T> then(Function<P, T> factory);

    /**
     * @param value value
     * @return io.github.kylinhunter.commons.tools.select.Branch<P, T>
     * @title
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:50 下午
     */
    default Branch<P, T> then(T value) {
        return then(p -> value);
    }

    /**
     * @param tester tester
     * @return io.github.kylinhunter.commons.tools.select.BranchBuilder<P, T>
     * @title
     * @description
     * @author BiJi'an
     * @date 2022/01/01 4:50 下午
     */
    static <P, T> BranchBuilder<P, T> of(Predicate<P> tester) {
        return factory -> Branch.of(tester, factory);
    }

}
