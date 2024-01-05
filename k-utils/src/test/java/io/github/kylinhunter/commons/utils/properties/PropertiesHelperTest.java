package io.github.kylinhunter.commons.utils.properties;

import io.github.kylinhunter.commons.io.file.TmpDirUtils;
import io.github.kylinhunter.commons.name.NameRule;
import io.github.kylinhunter.commons.utils.properties.data.Grandfather;
import io.github.kylinhunter.commons.utils.properties.data.TestDataHelper;
import java.io.File;
import java.util.Properties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PropertiesHelperTest {

  @Test
  public void testToBeanAndToProperties() {
    Grandfather grandfather1 = TestDataHelper.prepareData();
    Properties properties = PropertiesHelper.toProperties(grandfather1);
    System.out.println("properties:" + properties);
    properties.forEach(
        (k, v) -> System.out.println(k + ":" + v));

    Grandfather grandfather2 = PropertiesHelper.toBean(properties, Grandfather.class);

    System.out.println("grandfather1:" + grandfather1);
    System.out.println("grandfather2:" + grandfather2);
    Assertions.assertEquals(grandfather1, grandfather2);

    Properties properties2 = PropertiesHelper.toProperties(grandfather2);
    System.out.println("properties2:" + properties2);

    Grandfather grandfather3 = PropertiesHelper.toBean(properties2, Grandfather.class);
    System.out.println("grandfather3:" + grandfather3);

    Assertions.assertEquals(grandfather1, grandfather3);
  }

  @Test
  void testStoreAndLoad() {
    Grandfather grandfather = TestDataHelper.prepareData();
    System.out.println("grandfather-before:" + grandfather);
    File propFile = TmpDirUtils.getUserFile("properties/store.properties");
    Properties properties = PropertiesHelper.store(grandfather, propFile);
    Assertions.assertEquals(286, properties.size());
    Grandfather loadGrandFather =
        PropertiesHelper.load(propFile.getAbsolutePath(), Grandfather.class);
    System.out.println("grandfather-after :" + loadGrandFather);
    Assertions.assertEquals(grandfather, loadGrandFather);
  }

  @Test
  public void testStoreSnake() {
    Grandfather grandfather = TestDataHelper.prepareData();
    System.out.println("grandfather:" + grandfather);
    File propFile = TmpDirUtils.getUserFile("properties/store_SNAKE_LOW_UNDERSCORE.properties");
    Properties properties =
        PropertiesHelper.store(grandfather, NameRule.SNAKE_LOW_UNDERSCORE, propFile);
    Assertions.assertEquals(286, properties.size());
    Grandfather loadGrandFather =
        PropertiesHelper.load(propFile.getAbsolutePath(), NameRule.CAMEL_LOW, Grandfather.class);
    System.out.println("loadGrandFather:" + loadGrandFather);
    Assertions.assertEquals(grandfather, loadGrandFather);
  }

  @Test
  public void testStoreCamel() {
    Grandfather grandfather = TestDataHelper.prepareData();
    System.out.println("grandfather:" + grandfather);
    File propFile = TmpDirUtils.getUserFile("properties/store_CAMEL_UPPER.properties");
    Properties properties = PropertiesHelper.store(grandfather, NameRule.CAMEL_UPPER, propFile);
    Assertions.assertEquals(286, properties.size());
    Grandfather loadGrandFather =
        PropertiesHelper.load(propFile.getAbsolutePath(), NameRule.CAMEL_LOW, Grandfather.class);
    System.out.println("loadGrandFather:" + loadGrandFather);
    Assertions.assertEquals(grandfather, loadGrandFather);
  }
}
