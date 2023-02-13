package io.github.kylinhunter.commons.component;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CFTest {

    @Test
    void get() {
        System.out.println("=a===");
        A a = CF.get(A.class);
        a.println();
        I i = CF.get(IT.CA);
        i.println();

        System.out.println("=b===");
        B b = CF.get(B.class);
        b.println();
        i = CF.get(IT.CB);
        i.println();

        System.out.println("=abi===");
        AB ab = CF.get(AB.class);
        Assertions.assertEquals(4, ab.getIs().size());
        for (I abi : ab.getIs()) {
            abi.println();
        }

        System.out.println("====");
        D d = CF.get(D.class);
        d.println();
        i = CF.get(D.class);
        i.println();

        System.out.println("====");
        E e = CF.get(E.class);
        e.println();
        i = CF.get(E.class);
        i.println();

        System.out.println("=i===");
        List<I> components = CF.getAll(I.class);
        Assertions.assertEquals(4, components.size());

        for (I component : components) {
            component.println();
        }

        System.out.println("=f===");
        F f = CF.get(F.class);
        f.println();
        Assertions.assertEquals(E.class, f.getPrimary().getClass());

        System.out.println("=g===");
        G g = CF.get(G.class);
        g.println();
        Assertions.assertEquals(A.class, g.getPrimary().getClass());

    }

}

