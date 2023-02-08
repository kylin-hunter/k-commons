package io.github.kylinhunter.commons.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import io.github.kylinhunter.commons.io.ResourceHelper;

class YamlHelperTest {

    @Test
    void test() throws IOException {
        String path = "io/github/kylinhunter/commons/yaml/yaml.yaml";

        try (InputStream inputStream = ResourceHelper.getInputStreamInClassPath(path)) {

            String yamlText = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            System.out.println("file string=> \n" + yamlText);

            YamlBean yamlBeanFromPath = YamlHelper.loadFromClassPath(YamlBean.class, path);
            System.out.println("yamlBeanFromPath:" + yamlBeanFromPath);
            YamlBean yamlBeanFromText = YamlHelper.loadFromText(YamlBean.class, yamlText);
            System.out.println("yamlBeanFromText:" + yamlBeanFromText);
            assertEquals(yamlBeanFromPath, yamlBeanFromText);

            String dumpText1 = YamlHelper.dumpAsMap(yamlBeanFromPath);
            System.out.println("dumpText1 =>\n" + dumpText1);
            assertTrue(dumpText1.contains("myMoney"));
            YamlBean yamlBeanFromDumpText1 = YamlHelper.loadFromText(YamlBean.class, dumpText1);
            System.out.println("yamlBeanFromDumpText1:" + yamlBeanFromDumpText1);
            assertEquals(yamlBeanFromPath, yamlBeanFromDumpText1);

            String dumpText2 = YamlHelper.dumpAsMap(yamlBeanFromPath, YamlType.SNAKE);
            System.out.println("dumpText2 =>\n" + dumpText2);
            assertTrue(dumpText2.contains("my_money"));
            YamlBean yamlBeanFromDumpText2 = YamlHelper.loadFromText(YamlBean.class, dumpText2);
            System.out.println("yamlBeanFromDumpText2:" + yamlBeanFromDumpText2);
            assertEquals(yamlBeanFromPath, yamlBeanFromDumpText2);

        }

    }

}