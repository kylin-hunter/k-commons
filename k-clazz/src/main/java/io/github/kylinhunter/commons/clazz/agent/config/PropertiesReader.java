package io.github.kylinhunter.commons.clazz.agent.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.io.ResourceHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:30
 **/
public class PropertiesReader {

    private Map<String, String> pp = new HashMap<>();

    public Properties load(String url) {
        Properties properties = new Properties();
        try (InputStream inputStream = ResourceHelper.getInputStream(url, ResourceHelper.PathType.FILESYSTEM)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;

    }

    public void load2(String url) {

        String[] strings = StringUtils.split(url, ';');
        if (strings != null && strings.length > 0) {
            for (String string : strings) {
                String[] strings2 = StringUtils.split(string, '=');
                if (strings2 != null && strings2.length == 2) {
                    pp.put(strings2[0], strings2[1]);
                }

            }
        }

    }
}
