package io.github.kylinhunter.commons;

import java.util.concurrent.TimeUnit;

import io.github.kylinhunter.commons.util.ThreadHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 00:28
 **/
public class Dog implements Animal {

    @Override
    public String name() {
        return "dog";
    }

    @Override
    public void speak() {
        System.out.println("woof");
        ThreadHelper.sleep(100, TimeUnit.MILLISECONDS);
        System.out.println("woof");
    }

    @Override
    public String answer(String question) {
        return this.name() + " answer ：" + question;
    }
}
