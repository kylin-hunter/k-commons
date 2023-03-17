package io.github.kylinhunter.commons.properties;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import io.github.kylinhunter.commons.bean.info.BeanIntrospector;
import io.github.kylinhunter.commons.bean.info.BeanIntrospectorCache;
import io.github.kylinhunter.commons.io.ResourceHelper;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:30
 **/
public class PropertiesHelper {

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

    public void toBean(Properties properties, Class<?> clazz) {

    }

    public static Properties toProperties(Object obj) {
        Properties properties = new Properties();
        BeanIntrospector beanIntrospector = BeanIntrospectorCache.get(obj.getClass());
        Map<String, PropertyDescriptor> pds = beanIntrospector.getPropertyDescriptors();

        Map<String, PropertyDescriptor> pds2 = pds.entrySet().stream().filter(en -> {
            PropertyDescriptor value = en.getValue();
            Class<?> propertyType = value.getPropertyType();
            final String typeName = propertyType.getName();
            if (typeName.equals("java.lang.Class")) {
                return false;
            }
            return true;
        }).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        pds2.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        return properties;

    }
}
