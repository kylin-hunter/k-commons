package io.github.kylinhunter.commons.properties;

import java.io.File;
import java.util.Properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import io.github.kylinhunter.commons.bean.info.Grandfather;
import io.github.kylinhunter.commons.bean.info.SimpleGrandfather;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.name.NameRule;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PropertiesHelperTest {

    @Test
    public void testToBeanAndToPropertiesSimple() {
        SimpleGrandfather grandfather = TestDataHelper.prepareSimpleData();
        Properties properties = PropertiesHelper.toProperties(grandfather);
        System.out.println("properties:" + properties);
        properties.forEach((k, v) -> {
            System.out.println(k + ":" + v);

        });

        SimpleGrandfather grandfather2 = PropertiesHelper.toBean(properties, SimpleGrandfather.class);
        System.out.println("grandfather2:" + grandfather2);
        Assertions.assertEquals(grandfather, grandfather2);
        //
        //        Properties properties2 = PropertiesHelper.toProperties(grandfather2);
        //        System.out.println("properties2:" + properties2);
        //
        //        SimpleGrandfather grandfather3 = PropertiesHelper.toBean(properties2, SimpleGrandfather.class);
        //        System.out.println("grandfather3:" + grandfather3);
        //
        //        Assertions.assertEquals(grandfather, grandfather3);
    }

    @Test
    @Order(1)
    void testStoreAndLoad() {
        Grandfather grandfather = TestDataHelper.prepareTestData();
        System.out.println("grandfather-before:" + grandfather);
        File propFile = UserDirUtils.getTmpFile("properties/store.properties");
        Properties properties = PropertiesHelper.store(grandfather, propFile);
        Assertions.assertEquals(83, properties.size());
        Grandfather loadGrandFather = PropertiesHelper.load(propFile.getAbsolutePath(), Grandfather.class);
        System.out.println("grandfather-after :" + loadGrandFather);
        Assertions.assertEquals(grandfather, loadGrandFather);

    }

    @Test
    @Order(2)
    public void testStoreSnake() {
        Grandfather grandfather = TestDataHelper.prepareTestData();
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
        Grandfather grandfather = TestDataHelper.prepareTestData();
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
        Grandfather grandfather = TestDataHelper.prepareTestData();
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
        Grandfather grandfather = TestDataHelper.prepareTestData();
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

}