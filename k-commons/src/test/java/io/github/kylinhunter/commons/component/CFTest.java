package io.github.kylinhunter.commons.component;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CFTest {

    @Test
    void get() {
        System.out.println("=a1===");

        A1 a1 = CF.get(A1.class);
        a1.println();
        I i = CF.get(IT.CA);
        i.println();

        System.out.println("=a2===");
        A2 a2 = CF.get(A2.class);
        a2.println();
        i = CF.get(IT.CB);
        i.println();

        System.out.println("=a3===");
        A3 a3 = CF.get(A3.class);
        Assertions.assertEquals(2, a3.getIs1().size()); // 特殊 因为 cconstructor 导致is1 就不改了
        Assertions.assertEquals(4, a3.getIs2().size());
        Assertions.assertEquals(A2.class, a3.getPrimary1().getClass()); // 特殊 因为 cconstructor 导致primary1 就不改了
        Assertions.assertEquals(B2.class, a3.getPrimary2().getClass());
        a3.println();

        Assertions.assertTrue(a3.isInit());

        System.out.println("=b1===");
        B1 b1 = CF.get(B1.class);
        b1.println();
        i = CF.get(B1.class);
        i.println();

        System.out.println("=b2===");
        B2 b2 = CF.get(B2.class);
        b2.println();
        i = CF.get(B2.class);
        i.println();

        System.out.println("=b3===");
        B3 b3 = CF.get(B3.class);
        Assertions.assertEquals(4, b3.getIs1().size());
        Assertions.assertEquals(4, b3.getIs2().size());
        Assertions.assertEquals(B2.class, b3.getPrimary1().getClass());
        Assertions.assertEquals(B2.class, b3.getPrimary2().getClass());
        b3.println();
        Assertions.assertTrue(b3.isInit());

        System.out.println("=all i===");
        List<I> components = CF.getAll(I.class);
        Assertions.assertEquals(4, components.size());

        for (I component : components) {
            component.println();
        }

    }

}

