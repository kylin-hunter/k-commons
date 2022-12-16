package io.github.kylinhunter.commons.bean;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import io.github.kylinhunter.commons.json.JsonUtils;
import io.github.kylinhunter.commons.serialize.ObjectBytesSerializer;
import io.github.kylinhunter.commons.xml.JAXBHelper;

class BeanCopyUtilsTest {

    @Test
    public void test() {

        BizBean bizBean = new BizBean();
        bizBean.setText("text");

        Bean1 bean1 = new Bean1();
        bean1.setName1("name1");
        bean1.setName2("name2");
        bean1.setJson(bizBean);
        bean1.setJsonList(Lists.newArrayList(bizBean));
        bean1.setBytes(bean1.getJson());
        bean1.setBytesList(bean1.getJsonList());

        bean1.setXml(bean1.getJson());
        //        bean1.setXmlList(bean1.getJsonList());

        Bean2 bean2 = new Bean2();
        BeanCopyUtils.copyProperties(bean1, bean2);

        Assertions.assertEquals(bean1.getName1(), bean2.getName1());
        Assertions.assertEquals(bean1.getName2() + "1", bean2.getName2());
        Assertions.assertEquals(JsonUtils.writeToString(bizBean), bean2.getJson());
        Assertions.assertEquals(JsonUtils.writeToString(Lists.newArrayList(bizBean)), bean2.getJsonList());
        Assertions.assertEquals(bizBean.getText(),
                ((BizBean) ObjectBytesSerializer.deserialize(bean2.getBytes())).getText());
        Assertions.assertEquals(Lists.newArrayList(bizBean).get(0).getText(),
                ((List<BizBean>) ObjectBytesSerializer.deserialize(bean2.getBytesList())).get(0).getText()
        );
        Assertions.assertEquals(bizBean.getText(), JAXBHelper.unmarshal(BizBean.class, bean2.getXml()).getText());

        Bean1 bean1Reverse = new Bean1();
        BeanCopyUtils.copyProperties(bean2, bean1Reverse);

        Assertions.assertEquals(bean1.getName1(), bean1Reverse.getName1());
        Assertions.assertEquals(bean1.getName2() + "11", bean1Reverse.getName2());
        Assertions.assertEquals(bean1.getJson().getText(), bean1Reverse.getJson().getText());
        Assertions.assertEquals(bean1.getJsonList().get(0).getText(), bean1Reverse.getJsonList().get(0).getText());

        Assertions.assertEquals(bean1.getBytes().getText(), bean1Reverse.getBytes().getText());
        Assertions.assertEquals(bean1.getBytesList().get(0).getText(), bean1Reverse.getBytesList().get(0).getText());

        Assertions.assertEquals(bean1.getXml().getText(), bean1Reverse.getXml().getText());

    }

}