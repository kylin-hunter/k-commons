package io.github.kylinhunter.commons.component;


/**
 * @author BiJi'an
 * @description
 * @date 2022-11-21 02:14
 **/
@C(order = 4)
public class A1 implements I {
    public void println() {
        System.out.println(this.getClass().getSimpleName());
    }
}