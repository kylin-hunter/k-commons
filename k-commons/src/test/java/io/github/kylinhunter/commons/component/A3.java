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
@C
@Getter
public class A3 {
    private final List<I> is;
    private final I primary;

    public void println() {
        System.out.print("all Interface=>");
        for (I i : is) {
            System.out.print(i.getClass().getSimpleName() + "/");
        }
        System.out.println();
        System.out.println("primary ==>" + primary.getClass().getSimpleName());
    }
}