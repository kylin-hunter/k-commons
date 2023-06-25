package io.github.kylinhunter.commons.utils.bean;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.serialize.ObjectBytesSerializer;
import io.github.kylinhunter.commons.utils.json.JsonUtils;
import io.github.kylinhunter.commons.utils.xml.JAXBHelper;
import io.github.kylinhunter.commons.utils.yaml.YamlHelper;
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

    Bean1 beanSource = new Bean1();
    beanSource.setName1("name1");
    beanSource.setName2("name2");
    beanSource.setIntValue1(1);
    beanSource.setIntValue2(2);
    beanSource.setLongValue1(1);
    beanSource.setLongValue2(2L);
    beanSource.setFloatValue1(1.123F);
    beanSource.setFloatValue2(0.123F);
    beanSource.setDoubleValue1(1.123D);
    beanSource.setDoubleValue2(0.123D);
    beanSource.setJson(subBean);
    beanSource.setJsonList(ListUtils.newArrayList(subBean));
    beanSource.setBytes(subBean);
    beanSource.setBytesList(ListUtils.newArrayList(subBean));

    beanSource.setXml(subBean);
    beanSource.setYaml(subBean);

    Bean2 beanDist = new Bean2();
    BeanCopyUtils.copyProperties(beanSource, beanDist, "name2");

    Assertions.assertEquals(beanSource.getName1(), beanDist.getName1());
    Assertions.assertNull(beanDist.getName2());
    Assertions.assertEquals(JsonUtils.writeToString(subBean), beanDist.getJson());
    Assertions.assertEquals(
        JsonUtils.writeToString(ListUtils.newArrayList(subBean)), beanDist.getJsonList());
    Assertions.assertEquals(
        subBean.getText(),
        ((SubBean) ObjectBytesSerializer.deserialize(beanDist.getBytes())).getText());
    Assertions.assertEquals(
        ListUtils.newArrayList(subBean).get(0).getText(),
        ((List<SubBean>) ObjectBytesSerializer.deserialize(beanDist.getBytesList()))
            .get(0)
            .getText());
    Assertions.assertEquals(
        subBean.getText(), JAXBHelper.unmarshal(SubBean.class, beanDist.getXml()).getText());
    Assertions.assertEquals(
        subBean.getText(), YamlHelper.loadFromText(SubBean.class, beanDist.getYaml()).getText());

    Bean1 bean1Reverse = new Bean1();
    beanDist.setName2("name2");
    BeanCopyUtils.copyProperties(beanDist, bean1Reverse, "name2");

    Assertions.assertEquals(beanSource.getName1(), bean1Reverse.getName1());
    Assertions.assertNull(bean1Reverse.getName2());

    Assertions.assertEquals(beanSource.getIntValue1(), bean1Reverse.getIntValue1());
    Assertions.assertEquals(beanSource.getIntValue2(), bean1Reverse.getIntValue2());
    Assertions.assertEquals(beanSource.getIntValue3(), bean1Reverse.getIntValue3());
    Assertions.assertEquals(beanSource.getLongValue1(), bean1Reverse.getLongValue1());
    Assertions.assertEquals(beanSource.getLongValue2(), bean1Reverse.getLongValue2());
    Assertions.assertEquals(beanSource.getLongValue3(), bean1Reverse.getLongValue3());

    Assertions.assertEquals(beanSource.getFloatValue1(), bean1Reverse.getFloatValue1());
    Assertions.assertEquals(beanSource.getFloatValue2(), bean1Reverse.getFloatValue2());
    Assertions.assertEquals(beanSource.getFloatValue3(), bean1Reverse.getFloatValue3());
    Assertions.assertEquals(beanSource.getDoubleValue1(), bean1Reverse.getDoubleValue1());
    Assertions.assertEquals(beanSource.getDoubleValue2(), bean1Reverse.getDoubleValue2());
    Assertions.assertEquals(beanSource.getDoubleValue3(), bean1Reverse.getDoubleValue3());

    Assertions.assertEquals(beanSource.getJson().getText(), bean1Reverse.getJson().getText());
    Assertions.assertEquals(
        beanSource.getJsonList().get(0).getText(), bean1Reverse.getJsonList().get(0).getText());

    Assertions.assertEquals(beanSource.getBytes().getText(), bean1Reverse.getBytes().getText());
    Assertions.assertEquals(
        beanSource.getBytesList().get(0).getText(), bean1Reverse.getBytesList().get(0).getText());

    Assertions.assertEquals(beanSource.getXml().getText(), bean1Reverse.getXml().getText());
    Assertions.assertEquals(beanSource.getYaml().getText(), bean1Reverse.getYaml().getText());
  }
}
