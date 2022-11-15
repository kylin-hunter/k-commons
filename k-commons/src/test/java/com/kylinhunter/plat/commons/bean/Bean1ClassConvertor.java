package com.kylinhunter.plat.commons.bean;

import com.kylinhunter.plat.commons.bean.copy.AbstractClassConvertor;

/**
 * @description:
 * @author: BiJi'an
 * @create: 2021-09-08 16:20
 **/

public class Bean1ClassConvertor extends AbstractClassConvertor<Bean1, Bean2> {
    public Bean1ClassConvertor(boolean reverse) {
        super(reverse);
    }

    public void forword(Bean1 source, Bean2 target) {
        target.setName2(source.getName2() + "1");
    }

    public void backward(Bean2 source, Bean1 target) {
        target.setName2(source.getName2() + "1");
    }
}
