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
public class B3 {
    private final I primary1;
    private final List<I> is1;

    @A
    private I primary2;
    @A
    private List<I> is2;

    public void println() {
        System.out.print("all Interface is1=>");
        for (I i : is1) {
            System.out.print(i.getClass().getSimpleName() + "/");
        }
        if (is2 != null) {
            System.out.print("all Interface is2=>");
            for (I i : is2) {
                System.out.print(i.getClass().getSimpleName() + "/");
            }
            System.out.println();
        }

        System.out.println("primary1 ==>" + primary1.getClass().getSimpleName());
        System.out.println("primary2 ==>" + primary2.getClass().getSimpleName());
    }

}