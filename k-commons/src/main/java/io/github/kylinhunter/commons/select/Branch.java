package io.github.kylinhunter.commons.select;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author BiJi'an
 * @description
 * @date 2022/01/01
 **/
public interface Branch<P, T> {

    Predicate<P> tester();

    Function<P, T> factory();

    /**
     * @param tester  tester
     * @param factory factory
     * @return io.github.kylinhunter.commons.tools.select.Branch<P, T>
     * @title of
     * @description
     * @author BiJi'an
     * @date 2022-05-31 15:59
     */
    static <P, T> Branch<P, T> of(Predicate<P> tester, Function<P, T> factory) {
        return new Branch<P, T>() {

            @Override
            public Predicate<P> tester() {
                return tester;
            }

            @Override
            public Function<P, T> factory() {
                return factory;
            }

        };
    }
}
