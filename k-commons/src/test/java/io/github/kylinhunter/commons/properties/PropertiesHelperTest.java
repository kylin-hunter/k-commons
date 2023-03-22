package io.github.kylinhunter.commons.properties;

import java.io.File;
import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.kylinhunter.commons.bean.info.Father;
import io.github.kylinhunter.commons.bean.info.Grandfather;
import io.github.kylinhunter.commons.bean.info.Son;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.name.NameRule;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PropertiesHelperTest {

    @Test
    @Order(1)
    void testStoreAndLoad() {
        Grandfather grandfather = prepareTestData();
        System.out.println("grandfather:" + grandfather);
        File propFile = UserDirUtils.getTmpFile("properties/store.properties");
        Properties properties = PropertiesHelper.store(grandfather, propFile);
        Assertions.assertEquals(83, properties.size());
        Grandfather loadGrandFather = PropertiesHelper.load(propFile.getAbsolutePath(), Grandfather.class);
        System.out.println("loadGrandFather:" + loadGrandFather);
        Assertions.assertEquals(grandfather, loadGrandFather);

    }

    @Test
    @Order(2)
    public void testStoreSnake() {
        Grandfather grandfather = prepareTestData();
        System.out.println("grandfather:" + grandfather);
        File propFile = UserDirUtils.getTmpFile("properties/store_SNAKE_LOW_UNDERSCORE.properties");
        Properties properties = PropertiesHelper.store(grandfather, NameRule.SNAKE_LOW_UNDERSCORE, propFile);
        Assertions.assertEquals(83, properties.size());
        Grandfather loadGrandFather = PropertiesHelper.load(propFile.getAbsolutePath(), NameRule.CAMEL_LOW,
                Grandfather.class);
        System.out.println("loadGrandFather:" + loadGrandFather);
        Assertions.assertEquals(grandfather, loadGrandFather);
    }

    @Test
    @Order(3)
    public void testStoreCamel() {
        Grandfather grandfather = prepareTestData();
        System.out.println("grandfather:" + grandfather);
        File propFile = UserDirUtils.getTmpFile("properties/store_CAMEL_UPPER.properties");
        Properties properties = PropertiesHelper.store(grandfather, NameRule.CAMEL_UPPER, propFile);
        Assertions.assertEquals(83, properties.size());
        Grandfather loadGrandFather = PropertiesHelper.load(propFile.getAbsolutePath(), NameRule.CAMEL_LOW,
                Grandfather.class);
        System.out.println("loadGrandFather:" + loadGrandFather);
        Assertions.assertEquals(grandfather, loadGrandFather);
    }

    @Test
    public void testToBeanAndToProperties1() {
        Grandfather grandfather = prepareTestData();
        Properties properties = PropertiesHelper.toProperties(grandfather);
        System.out.println("properties:" + properties);

        Grandfather grandfather2 = PropertiesHelper.toBean(properties, Grandfather.class);
        System.out.println("grandfather2:" + grandfather2);
        Assertions.assertEquals(grandfather, grandfather2);

        Properties properties2 = PropertiesHelper.toProperties(grandfather2);
        System.out.println("properties2:" + properties2);

        Grandfather grandfather3 = PropertiesHelper.toBean(properties2, Grandfather.class);
        System.out.println("grandfather3:" + grandfather3);

        Assertions.assertEquals(grandfather, grandfather3);

    }

    @Test
    public void testToBeanAndToProperties2() {
        Grandfather grandfather = prepareTestData();
        Properties properties = PropertiesHelper.toProperties(grandfather, NameRule.SNAKE_LOW_UNDERSCORE);
        System.out.println("properties:" + properties);

        Grandfather grandfather2 = PropertiesHelper.toBean(properties, NameRule.CAMEL_LOW, Grandfather.class);
        System.out.println("grandfather2:" + grandfather2);
        Assertions.assertEquals(grandfather, grandfather2);

        Properties properties2 = PropertiesHelper.toProperties(grandfather2, NameRule.CAMEL_UPPER);
        System.out.println("properties2:" + properties2);

        Grandfather grandfather3 = PropertiesHelper.toBean(properties2, NameRule.CAMEL_LOW, Grandfather.class);
        System.out.println("grandfather3:" + grandfather3);

        Assertions.assertEquals(grandfather, grandfather3);

    }

    private Grandfather prepareTestData() {
        Father father = createFather();
        Son son = createSon();
        father.setSon(son);
        Grandfather grandfather = createGrandFather();
        grandfather.setFather1(father);
        grandfather.setFather2(father);
        return grandfather;
    }

    public Grandfather createGrandFather() {
        Grandfather grandfather = new Grandfather();
        grandfather.setP01("1");
        grandfather.setP02((short) 2);
        grandfather.setP03((short) 3);
        grandfather.setP04(4);
        grandfather.setP05(5);
        grandfather.setP06(6l);
        grandfather.setP07(7L);
        grandfather.setP08(8.1f);
        grandfather.setP09(9.1F);
        grandfather.setP10(10.1d);
        grandfather.setP11(10.2D);
        grandfather.setP12(true);
        grandfather.setP13(true);
        grandfather.setSpecialPeople("People");
        grandfather.setSpecialGrandFather("grandFather");
        return grandfather;

    }

    public Father createFather() {
        Father father = new Father();
        father.setP01("1");
        father.setP02((short) 2);
        father.setP03((short) 3);
        father.setP04(4);
        father.setP05(5);
        father.setP06(6l);
        father.setP07(7L);
        father.setP08(8.1f);
        father.setP09(9.1F);
        father.setP10(10.1d);
        father.setP11(10.2D);
        father.setP12(true);
        father.setP13(true);
        father.setSpecialPeople("People");
        father.setSpecialFather("father");
        return father;

    }

    public Son createSon() {
        Son son = new Son();
        son.setP01("1");
        son.setP02((short) 2);
        son.setP03((short) 3);
        son.setP04(4);
        son.setP05(5);
        son.setP06(6l);
        son.setP07(7L);
        son.setP08(8.1f);
        son.setP09(9.1F);
        son.setP10(10.1d);
        son.setP11(10.2D);
        son.setP12(true);
        son.setP13(true);
        son.setSpecialPeople("People");
        son.setSpecialSon("son");
        return son;
    }

}