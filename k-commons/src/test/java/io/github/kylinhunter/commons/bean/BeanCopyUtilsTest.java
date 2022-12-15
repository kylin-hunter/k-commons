package io.github.kylinhunter.commons.bean;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import io.github.kylinhunter.commons.json.JsonUtils;
import io.github.kylinhunter.commons.serialize.ObjectSerializer;

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
        bean1.setBytes1(bean1.getJson());
        bean1.setBytes2(bean1.getJsons());

        Bean2 bean2 = new Bean2();
        BeanCopyUtils.copyProperties(bean1, bean2);

        Assertions.assertEquals("name1", bean2.getName1());
        Assertions.assertEquals("name21", bean2.getName2());
        Assertions.assertEquals(JsonUtils.writeToString(beanJson), bean2.getJson());
        Assertions.assertEquals(JsonUtils.writeToString(Lists.newArrayList(beanJson)), bean2.getJsons());
        Assertions.assertEquals(beanJson.getJson(),
                ((BeanJson) ObjectSerializer.deserialize(bean2.getBytes1())).getJson());
        Assertions.assertEquals(Lists.newArrayList(beanJson).get(0).getJson(),
                ((List<BeanJson>) ObjectSerializer.deserialize(bean2.getBytes2())).get(0).getJson()
        );

        Bean1 bean1Reverse = new Bean1();
        BeanCopyUtils.copyProperties(bean2, bean1Reverse);

        Assertions.assertEquals(bean1.getName1(), bean1Reverse.getName1());
        Assertions.assertEquals(bean1.getName2() + "11", bean1Reverse.getName2());
        Assertions.assertEquals(bean1.getJson().getJson(), bean1Reverse.getJson().getJson());
        Assertions.assertEquals(bean1.getJsons().get(0).getJson(), bean1Reverse.getJsons().get(0).getJson());

        Assertions.assertEquals(bean1.getBytes1().getJson(), bean1Reverse.getBytes1().getJson());
        Assertions.assertEquals(bean1.getBytes2().get(0).getJson(), bean1Reverse.getBytes2().get(0).getJson());

    }

}