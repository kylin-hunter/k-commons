package io.github.kylinhunter.commons.utils.properties.data;

import io.github.kylinhunter.commons.collections.ListUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-01 23:31
 */
public class TestDataHelper {

  public static Grandfather prepareData() {
    Father father = createSimpleFather();
    Son son0 = createSon(0, false, true);
    Son son1 = createSon(1, true, true);
    Son son2 = createSon(2, true, true);
    Son son3 = createSon(3, true, true);
    Son son4 = createSon(4, true, true);

    father.setSon(son0);
    father.setSonList(ListUtils.newArrayList(son1, son2));
    father.setSonArrays(new Son[]{son3, son4});
    Grandfather grandfather = createSimpleGrandfather();
    grandfather.setFather(father);
    grandfather.setName("grandfather-name");
    return grandfather;
  }

  private static Grandfather createSimpleGrandfather() {
    Grandfather grandfather = new Grandfather();
    grandfather.setName("grandfather");
    return grandfather;
  }

  private static Father createSimpleFather() {
    Father father = new Father();
    father.setName("father-name");
    return father;
  }

  private static Son createSon(int index, boolean withName, boolean withGrandSon) {

    Son son = new Son();
    if (withName) {
      son.setName("son-name" + index);
    }

    if (withGrandSon) {
      GrandSon grandSon1 = createSimpleGrandSon("(" + "son-name" + index + ")" + "grandson-name1");
      GrandSon grandSon2 = createSimpleGrandSon("(" + "son-name" + index + ")" + "grandson-name2");
      son.setGrandSonArr(new GrandSon[]{grandSon1, grandSon2});
      son.setGrandSonList(ListUtils.newArrayList(grandSon1, grandSon2));
    }

    return son;
  }

  private static GrandSon createSimpleGrandSon(String name) {
    GrandSon grandSon = new GrandSon();
    grandSon.setName(name);
    grandSon.setP01("1");
    grandSon.setP02((short) 2);
    grandSon.setP03((short) 3);
    grandSon.setP04(4);
    grandSon.setP05(5);
    grandSon.setP06(6L);
    grandSon.setP07(7L);
    grandSon.setP08(8.1f);
    grandSon.setP09(9.1F);
    grandSon.setP10(10.1d);
    grandSon.setP11(10.2D);
    grandSon.setP12(true);
    grandSon.setP13(true);
    return grandSon;
  }
}
