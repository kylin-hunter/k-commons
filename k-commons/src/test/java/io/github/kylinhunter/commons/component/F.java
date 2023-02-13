package io.github.kylinhunter.commons.component;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-21 02:14
 **/

@RequiredArgsConstructor
@Getter
public class F {
    private final List<I> is;
    private final I primary;

    public void println() {
        System.out.println("f");
        for (I i : is) {
            System.out.println("f=>" + i.getClass().getName());
            i.println();
        }
        System.out.println("f primary i");
        primary.println();
    }
}