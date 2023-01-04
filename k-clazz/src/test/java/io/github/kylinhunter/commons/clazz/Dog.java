package io.github.kylinhunter.commons.clazz;

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
        sleep(100);
        System.out.println("woof");
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String answer(String question) {
        return this.name() + " answer ：" + question;
    }
}
