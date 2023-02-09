package io.github.kylinhunter.commons.component;

import java.util.Set;

import org.junit.jupiter.api.Test;

class CFTest {

    @Test
    void get() {
        A a = CF.get(A.class);
        a.println();
        I i = CF.get(IT.CA);
        i.println();

        System.out.println("====");
        B b = CF.get(B.class);
        b.println();
        i = CF.get(IT.CB);
        i.println();

        System.out.println("====");
        D d = CF.get(D.class);
        d.println();
        i = CF.get(D.class);
        i.println();

        System.out.println("====");
        Set<I> components = CF.getComponents(I.class);
        for (I component : components) {
            component.println();
        }

    }

}

