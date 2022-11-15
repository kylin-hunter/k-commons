package com.kylinhunter.plat.commons.bean;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;
import com.kylinhunter.plat.commons.util.JsonUtils;

class BeanCopyUtilsTest {

    @Test
    public void test() {

        BeanJson beanJson = new BeanJson();
        beanJson.setJson("json");

        Bean1 bean1 = new Bean1();
        bean1.setName1("name1");
        bean1.setName2("name2");
        bean1.setJson(beanJson);
        bean1.setJsons(Lists.newArrayList(beanJson));

        Bean2 bean2 = new Bean2();
        BeanCopyUtils.copyProperties(bean1, bean2);

        Assertions.assertEquals("name1", bean2.getName1());
        Assertions.assertEquals("name21", bean2.getName2());
        Assertions.assertEquals(JsonUtils.toString(beanJson), bean2.getJson());
        Assertions.assertEquals(JsonUtils.toString(Lists.newArrayList(beanJson)), bean2.getJsons());

        Bean1 bean1Reverse = new Bean1();
        BeanCopyUtils.copyProperties(bean2, bean1Reverse);

        Assertions.assertEquals(bean1.getName1(), bean1Reverse.getName1());
        Assertions.assertEquals(bean1.getName2() + "11", bean1Reverse.getName2());
        Assertions.assertEquals(bean1.getJson().getJson(), bean1Reverse.getJson().getJson());
        Assertions.assertEquals(bean1.getJsons().get(0).getJson(), bean1Reverse.getJsons().get(0).getJson());

    }

}