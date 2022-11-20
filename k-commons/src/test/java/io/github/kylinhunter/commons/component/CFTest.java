package io.github.kylinhunter.commons.component;

import org.junit.jupiter.api.Test;

class CFTest {

    @Test
    void get() {
        A a = CF.get(A.class);
        a.println();
        B b = CF.get(B.class);
        b.println();

    }

}

