package io.github.kylinhunter.commons.clazz.test;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-07 00:51
 **/
public class ApiTest {
    public static void main(String[] args) {
        System.out.println("12" + new ApiTest().aaa("111"));
        //线程一
        new Thread(() -> new ApiTest().http_lt1()).start();

        //线程二
        new Thread(() -> {
            new ApiTest().http_lt1();
        }).start();
    }

    public void http_lt1() {
        System.out.println("测试结果：hi1");
        http_lt2();
    }

    public void http_lt2() {
        System.out.println("测试结果：hi2");
        http_lt3();
    }

    public void http_lt3() {
        System.out.println("测试结果：hi3");
    }

    public String aaa(String name) {
        return "123";
    }

}
