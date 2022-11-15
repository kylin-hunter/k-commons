package com.kylinhunter.plat.commons.util;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReflectionUtilTest {

    @Test
    void test() {

        Map<String, Field> allDeclaredFields = ReflectionUtil.getAllDeclaredFields(Child.class, true);
        Assertions.assertEquals(6, allDeclaredFields.size());
        System.out.println("withSuper========");
        allDeclaredFields.forEach((k, v) -> System.out.println(k + ":" + v.getName()));

        allDeclaredFields = ReflectionUtil.getAllDeclaredFields(Child.class, false);
        Assertions.assertEquals(3, allDeclaredFields.size());
        System.out.println("this========");
        allDeclaredFields.forEach((k, v) -> System.out.println(k + ":" + v.getName()));

    }

    public static class Child extends Parent {
        private String childPro1;
        protected String childPro2;
        public String childPro3;

    }

    public static class Parent {
        private String parentPro1;
        protected String parentPro2;
        public String parentPro3;

    }

}