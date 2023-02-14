package io.github.kylinhunter.commons.component;

import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-09 23:36
 **/
@CC
public class CompConfiguration {

    @C(order = 2)
    public B1 d() {
        return new B1();
    }

    @C(primary = true, order = 1)
    public B2 e(B1 b1) {
        return new B2(b1);
    }

    @C
    public B3 f(List<I> is, I i) {
        return new B3(i, is);
    }
}
