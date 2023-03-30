package io.github.kylinhunter.commons.properties;

import io.github.kylinhunter.commons.bean.info.Father;
import io.github.kylinhunter.commons.bean.info.Grandfather;
import io.github.kylinhunter.commons.bean.info.SimpleFather;
import io.github.kylinhunter.commons.bean.info.SimpleGrandSon;
import io.github.kylinhunter.commons.bean.info.SimpleGrandfather;
import io.github.kylinhunter.commons.bean.info.SimpleSon;
import io.github.kylinhunter.commons.bean.info.Son;
import io.github.kylinhunter.commons.collections.ListUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-01 23:31
 **/
public class TestDataHelper {

    public static SimpleGrandfather prepareSimpleData() {
        SimpleFather father = createSimpleFather();
        SimpleSon son = createSimpleSon(false, true);
        SimpleSon son1 = createSimpleSon(true, true);
        SimpleSon son2 = createSimpleSon(true, true);

        father.setSon(son);
        father.setSonList(ListUtils.newArrayList(son1, son2));
        father.setSonArrays(new SimpleSon[] {son1, son2});
        SimpleGrandfather grandfather = createSimpleGrandfather();
        grandfather.setFather(father);
        grandfather.setName("grandfather-name");
        return grandfather;
    }

    private static SimpleGrandfather createSimpleGrandfather() {
        SimpleGrandfather grandfather = new SimpleGrandfather();
        grandfather.setName("grandfather");
        return grandfather;

    }

    private static SimpleFather createSimpleFather() {
        SimpleFather father = new SimpleFather();
        father.setName("father-name");
        return father;

    }

    private static SimpleSon createSimpleSon(boolean withName, boolean withGrandSon) {

        SimpleSon son = new SimpleSon();
        if (withGrandSon) {
            SimpleGrandSon simpleGrandSon1 = new SimpleGrandSon("grandson-name1");
            SimpleGrandSon simpleGrandSon2 = new SimpleGrandSon("grandson-name2");
            son.setGrandSonArr(new SimpleGrandSon[]{simpleGrandSon1,simpleGrandSon2});
            son.setGrandSonList(ListUtils.newArrayList(simpleGrandSon1,simpleGrandSon2));

        }
        if (withName) {
            son.setName("son-name");
        }

        return son;
    }

    public static Grandfather prepareTestData() {
        Father father = createFather();
        Son son = createSon();
        Son son1 = createSon();
        Son son2 = createSon();
        Son son3 = createSon();
        Son son4 = createSon();
        father.setSon(son);
        father.setSonList(ListUtils.newArrayList(son1, son2));
        father.setSonArrays(new Son[] {son3, son4});
        Grandfather grandfather = createGrandFather();
        grandfather.setFather1(father);
        grandfather.setFather2(father);
        return grandfather;
    }

    private static Grandfather createGrandFather() {
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

    private static Father createFather() {
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

    private static Son createSon() {
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
