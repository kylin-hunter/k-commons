package io.github.kylinhunter.commons.yaml;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            System.out.println("string From file=>\n" + yamlText);

            YamlBean yamlBean1 = YamlHelper.loadFromClassPath(YamlBean.class, path);
            System.out.println("yamlBean1:\n" + yamlBean1);
            YamlBean yamlBean2 = YamlHelper.loadFromText(YamlBean.class, yamlText);
            System.out.println("yamlBean2:\n" + yamlBean2);
            assertEquals(yamlBean1, yamlBean2);

            String dumpText = YamlHelper.dumpAsMap(yamlBean1);
            System.out.println("string from yamlBean1 =>\n" + dumpText);
            YamlBean yamlBean3 = YamlHelper.loadFromText(YamlBean.class, dumpText);
            System.out.println("yamlBean3:\n" + yamlBean3);
            assertEquals(yamlBean1, yamlBean3);
        }

    }

}