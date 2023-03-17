package io.github.kylinhunter.commons.properties;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.bean.info.Grandfather;
import io.github.kylinhunter.commons.bean.info.Father;

class PropertiesHelperTest {

    @Test
    void toProperties() {
        Grandfather bean = new Grandfather();
        Father father = new Father();
        bean.setFather(father);
        Properties properties = PropertiesHelper.toProperties(bean);
        properties.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });

    }
}