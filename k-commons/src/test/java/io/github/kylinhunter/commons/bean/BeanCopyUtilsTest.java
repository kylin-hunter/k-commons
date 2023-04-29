package io.github.kylinhunter.commons.bean;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.json.JsonUtils;
import io.github.kylinhunter.commons.serialize.ObjectBytesSerializer;
import io.github.kylinhunter.commons.xml.JAXBHelper;
import io.github.kylinhunter.commons.yaml.YamlHelper;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BeanCopyUtilsTest {

    @SuppressWarnings("unchecked")
    @Test
    public void test() {

        SubBean subBean = new SubBean();
        subBean.setId(99);
        subBean.setText("text");

        Bean1 bean1 = new Bean1();
        bean1.setName1("name1");
        bean1.setName2("name2");
        bean1.setIntValue1(1);
        bean1.setIntValue2(2);
        bean1.setLongValue1(1);
        bean1.setLongValue2(2L);
        bean1.setFloatValue1(1.123F);
        bean1.setFloatValue2(0.123F);
        bean1.setDoubleValue1(1.123D);
        bean1.setDoubleValue2(0.123D);
        bean1.setJson(subBean);
        bean1.setJsonList(ListUtils.newArrayList(subBean));
        bean1.setBytes(subBean);
        bean1.setBytesList(ListUtils.newArrayList(subBean));

        bean1.setXml(subBean);
        bean1.setYaml(subBean);

        Bean2 bean2 = new Bean2();
        BeanCopyUtils.copyProperties(bean1, bean2, "name2");

        Assertions.assertEquals(bean1.getName1(), bean2.getName1());
        Assertions.assertNull(bean2.getName2());
        Assertions.assertEquals(JsonUtils.writeToString(subBean), bean2.getJson());
        Assertions.assertEquals(JsonUtils.writeToString(ListUtils.newArrayList(subBean)), bean2.getJsonList());
        Assertions.assertEquals(subBean.getText(),
                ((SubBean) ObjectBytesSerializer.deserialize(bean2.getBytes())).getText());
        Assertions.assertEquals(ListUtils.newArrayList(subBean).get(0).getText(),
                ((List<SubBean>) ObjectBytesSerializer.deserialize(bean2.getBytesList())).get(0).getText()
        );
        Assertions.assertEquals(subBean.getText(), JAXBHelper.unmarshal(SubBean.class, bean2.getXml()).getText());
        Assertions.assertEquals(subBean.getText(), YamlHelper.loadFromText(SubBean.class, bean2.getYaml()).getText());

        Bean1 bean1Reverse = new Bean1();
        bean2.setName2("name2");
        BeanCopyUtils.copyProperties(bean2, bean1Reverse, "name2");

        Assertions.assertEquals(bean1.getName1(), bean1Reverse.getName1());
        Assertions.assertNull(bean1Reverse.getName2());

        Assertions.assertEquals(bean1.getIntValue1(), bean1Reverse.getIntValue1());
        Assertions.assertEquals(bean1.getIntValue2(), bean1Reverse.getIntValue2());
        Assertions.assertEquals(bean1.getIntValue3(), bean1Reverse.getIntValue3());
        Assertions.assertEquals(bean1.getLongValue1(), bean1Reverse.getLongValue1());
        Assertions.assertEquals(bean1.getLongValue2(), bean1Reverse.getLongValue2());
        Assertions.assertEquals(bean1.getLongValue3(), bean1Reverse.getLongValue3());

        Assertions.assertEquals(bean1.getFloatValue1(), bean1Reverse.getFloatValue1());
        Assertions.assertEquals(bean1.getFloatValue2(), bean1Reverse.getFloatValue2());
        Assertions.assertEquals(bean1.getFloatValue3(), bean1Reverse.getFloatValue3());
        Assertions.assertEquals(bean1.getDoubleValue1(), bean1Reverse.getDoubleValue1());
        Assertions.assertEquals(bean1.getDoubleValue2(), bean1Reverse.getDoubleValue2());
        Assertions.assertEquals(bean1.getDoubleValue3(), bean1Reverse.getDoubleValue3());

        Assertions.assertEquals(bean1.getJson().getText(), bean1Reverse.getJson().getText());
        Assertions.assertEquals(bean1.getJsonList().get(0).getText(), bean1Reverse.getJsonList().get(0).getText());

        Assertions.assertEquals(bean1.getBytes().getText(), bean1Reverse.getBytes().getText());
        Assertions.assertEquals(bean1.getBytesList().get(0).getText(), bean1Reverse.getBytesList().get(0).getText());

        Assertions.assertEquals(bean1.getXml().getText(), bean1Reverse.getXml().getText());
        Assertions.assertEquals(bean1.getYaml().getText(), bean1Reverse.getYaml().getText());

    }

}