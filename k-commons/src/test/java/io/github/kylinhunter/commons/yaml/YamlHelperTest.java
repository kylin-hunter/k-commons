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

            YamlBean yamlBeanFromPath = YamlHelper.loadFromClassPath(YamlBean.class, path, true);
            System.out.println("yamlBeanFromPath:" + yamlBeanFromPath);
            YamlBean yamlBeanFromText = YamlHelper.loadFromText(YamlBean.class, yamlText, true);
            System.out.println("yamlBeanFromText:" + yamlBeanFromText);
            assertEquals(yamlBeanFromPath, yamlBeanFromText);

            String dumpText1 = YamlHelper.dumpAsMap(yamlBeanFromPath);
            System.out.println("dumpText1 =>\n" + dumpText1);
            assertTrue(dumpText1.contains("myMoney"));
            YamlBean yamlBeanFromDumpText1 = YamlHelper.loadFromText(YamlBean.class, dumpText1);
            System.out.println("yamlBeanFromDumpText1:" + yamlBeanFromDumpText1);
            assertEquals(yamlBeanFromPath, yamlBeanFromDumpText1);

            String dumpText2 = YamlHelper.dumpAsMap(yamlBeanFromPath, YamlType.SNAKE_UNDERSCORE);
            System.out.println("dumpText2 =>\n" + dumpText2);
            assertTrue(dumpText2.contains("my_money"));
            YamlBean yamlBeanFromDumpText2 = YamlHelper.loadFromText(YamlBean.class, dumpText2, true);
            System.out.println("yamlBeanFromDumpText2:" + yamlBeanFromDumpText2);
            assertEquals(yamlBeanFromPath, yamlBeanFromDumpText2);

            String dumpText3 = YamlHelper.dumpAsMap(yamlBeanFromPath, YamlType.SNAKE_HYPHEN);
            System.out.println("dumpText3 =>\n" + dumpText3);
            assertTrue(dumpText3.contains("my-money"));
            YamlBean yamlBeanFromDumpText3 = YamlHelper.loadFromText(YamlBean.class, dumpText3, true);
            System.out.println("yamlBeanFromDumpText3:" + yamlBeanFromDumpText3);
            assertEquals(yamlBeanFromPath, yamlBeanFromDumpText3);
        }

    }

}