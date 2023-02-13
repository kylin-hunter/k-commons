package io.github.kylinhunter.commons.component;

import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-09 23:36
 **/
@CC
public class CompConfiguration {

    @C
    public D d() {
        return new D();
    }

    @C(primary = true)
    public E e(D d) {
        return new E(d);
    }

    @C
    public F f(List<I> is, I i) {
        return new F(is, i);
    }
}
