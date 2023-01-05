package io.github.kylinhunter.commons.clazz.javassist;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.kylinhunter.commons.clazz.Animal;
import io.github.kylinhunter.commons.clazz.exception.ClazzException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TimeCostHelperTest {

    @Test
    @Order(1)
    void watch() throws ClazzException, IllegalAccessException, InstantiationException {

        KMethod method1 = new KMethod("name");
        KMethod method2 = new KMethod("speak");
        KMethod method3 = new KMethod("answer", String.class);
        final Class<?> watch =
                TimeCostHelper.watch("io.github.kylinhunter.commons.clazz.Dog", method1, method2, method3);

        Animal dog = (Animal) watch.newInstance();
        dog.name();
        dog.speak();
        dog.answer("hello");
    }

}