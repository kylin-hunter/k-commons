package io.github.kylinhunter.commons.component;

import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-21 02:14
 **/

@RequiredArgsConstructor
public class E implements I {
    private final D d;

    public void println() {
        System.out.println("e and d.println");
        d.println();
    }
}