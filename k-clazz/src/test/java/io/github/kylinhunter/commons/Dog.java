package io.github.kylinhunter.commons;

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

        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("woof");
    }

    @Override
    public String answer(String question) {
        return this.name() + " answer ：" + question;
    }
}
