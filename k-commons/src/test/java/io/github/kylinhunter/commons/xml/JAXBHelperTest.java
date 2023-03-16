package io.github.kylinhunter.commons.xml;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.collections.ListUtils;

class JAXBHelperTest {

    @Test
    void test() {

        List<Child> children = ListUtils.newArrayList();
        children.add(new Child(3, 4));
        children.add(new Child(5, 6));
        Parent parent1 = new Parent(1, 2, children);
        final String xmlStr = JAXBHelper.marshal(parent1);
        System.out.println("xmlStr=>" + xmlStr);

        Parent parent2 = JAXBHelper.unmarshal(Parent.class, xmlStr);
        Assertions.assertEquals(1, parent2.getValue1());
        Assertions.assertEquals(2, parent2.getChildren().size());

        Assertions.assertEquals(parent1, parent2);

    }

}