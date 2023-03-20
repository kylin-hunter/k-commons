package io.github.kylinhunter.commons.properties;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.bean.info.BeanIntrospector;
import io.github.kylinhunter.commons.bean.info.BeanIntrospectors;
import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.date.DateUtils;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.Charsets;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.properties.ObjectDescriptor.ObjectFileds;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import io.github.kylinhunter.commons.strings.CharsetConst;
import io.github.kylinhunter.commons.util.ObjectValues;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:30
 **/
public class PropertiesHelper {

    /**
     * @param path path
     * @return java.util.Properties
     * @title load
     * @description
     * @author BiJi'an
     * @date 2023-03-19 01:28
     */
    public static Properties load(String path) {
        Properties properties = new Properties();
        try (InputStream inputStream = ResourceHelper.getInputStream(path, ResourceHelper.PathType.FILESYSTEM)) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new KIOException("load error", e);
        }
        return properties;

    }

    /**
     * @param path  path
     * @param clazz clazz
     * @return T
     * @title load
     * @description
     * @author BiJi'an
     * @date 2023-03-19 14:10
     */
    public static <T> T load(String path, Class<T> clazz) {
        Properties properties = load(path);
        return toBean(properties, clazz);

    }

    /**
     * @param properties properties
     * @param clazz      clazz
     * @return T
     * @title toBean
     * @description
     * @author BiJi'an
     * @date 2023-03-19 01:21
     */
    public static <T> T toBean(Properties properties, Class<T> clazz) {
        Map<ObjecId, ObjectDescriptor> data = MapUtils.newHashMap();
        properties.forEach((name, propValue) -> {
            String propName = (String) name;
            ObjecId objecId = new ObjecId(propName);
            data.compute(objecId, (oid, objectDescriptor) -> {
                if (objectDescriptor == null) {
                    objectDescriptor = new ObjectDescriptor(oid);
                }
                objectDescriptor.addField(propName, propValue);
                return objectDescriptor;
            });
        });
        T rootObject = ObjectCreator.create(clazz);
        BeanIntrospector beanIntrospector = BeanIntrospectors.get(clazz);

        Map<String, Object> objectPool = MapUtils.newHashMap();
        Stream<ObjectDescriptor> objectDescriptors = data.values().stream().sorted();
        objectDescriptors.forEach(objectDescriptor -> {
            ObjecId objecId = objectDescriptor.getObjecId();
            ObjectFileds fields = objectDescriptor.getFields();
            if (objecId.getLevel() == 1) {
                fields.forEach((name, field) -> {
                    PropertyDescriptor propertyDescriptor = beanIntrospector.getPropertyDescriptor(name);
                    Method method = propertyDescriptor.getWriteMethod();
                    Object value = ObjectValues.get(field.getValue(), method.getParameterTypes()[0]);
                    ReflectUtils.invoke(rootObject, method, value);
                });
                objectPool.put(objecId.getId(), rootObject);
            } else {
                Object parentObj = objectPool.get(objecId.getParentId());
                PropertyDescriptor objPropertyDescriptor = beanIntrospector.getFullPropertyDescriptor(objecId.getId());
                Method writeMethod = objPropertyDescriptor.getWriteMethod();
                Object object = ObjectCreator.create(writeMethod.getParameterTypes()[0]);
                ReflectUtils.invoke(parentObj, writeMethod, object);
                fields.forEach((n, f) -> {
                    PropertyDescriptor propertyDescriptor = beanIntrospector.getFullPropertyDescriptor(n);
                    Method method = propertyDescriptor.getWriteMethod();
                    Object value = ObjectValues.get(f.getValue(), method.getParameterTypes()[0]);
                    ReflectUtils.invoke(object, method, value);
                });
                objectPool.put(objecId.getId(), object);

            }
        });
        return rootObject;
    }

    /**
     * @param obj  obj
     * @param file file
     * @return void
     * @title store
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:47
     */
    public static Properties store(Object obj, File file) {
        return store(obj, file, CharsetConst.UTF_8);
    }

    /**
     * @param obj     obj
     * @param file    file
     * @param charset charset
     * @return void
     * @title store
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:53
     */
    public static Properties store(Object obj, File file, String charset) {
        Properties properties = toProperties(obj);
        return store(properties, file, Charsets.toCharset(charset));

    }

    /**
     * @param properties properties
     * @param file       file
     * @param charset    charset
     * @return void
     * @title store
     * @description
     * @author BiJi'an
     * @date 2023-03-19 01:03
     */
    public static Properties store(Properties properties, File file, String charset) {
        return store(properties, file, Charsets.toCharset(charset));
    }

    /**
     * @param properties properties
     * @param file       file
     * @param charset    charset
     * @return void
     * @title store
     * @description
     * @author BiJi'an
     * @date 2023-03-19 01:03
     */
    public static Properties store(Properties properties, File file, Charset charset) {
        try (FileOutputStream out = new FileOutputStream(file);
             OutputStreamWriter fileWriter = new OutputStreamWriter(out, charset)) {
            properties.store(fileWriter, "auto create properties at " + DateUtils.formatNow());
            return properties;
        } catch (Exception e) {
            throw new KIOException("store  error", e);
        }

    }

    /**
     * @param obj obj
     * @return java.util.Properties
     * @title toProperties
     * @description
     * @author BiJi'an
     * @date 2023-03-19 01:06
     */
    public static Properties toProperties(Object obj) {
        Properties properties = new Properties();
        BeanIntrospector beanIntrospector = BeanIntrospectors.get(obj.getClass());
        Map<String, PropertyDescriptor> pds = beanIntrospector.getPropertyDescriptors();
        for (Map.Entry<String, PropertyDescriptor> entry : pds.entrySet()) {
            PropertyDescriptor propertyDescriptor = entry.getValue();
            toProperties(properties, "", obj, propertyDescriptor);
        }
        return properties;
    }

    public static void toProperties(Properties properties, String parent, Object obj, PropertyDescriptor pd) {
        if (!StringUtils.isEmpty(parent)) {
            parent += ".";
        }
        String propName = parent + pd.getName();
        Method readMethod = pd.getReadMethod();
        Object result = ReflectUtils.invoke(obj, readMethod);
        Class<?> returnType = readMethod.getReturnType();
        if (result != null) {
            if (ClassUtils.isPrimitiveOrWrapper(returnType)) {
                properties.put(propName, String.valueOf(result));
            } else if (returnType == String.class) {
                properties.put(propName, result);
            } else {
                BeanIntrospector beanIntrospector = BeanIntrospectors.get(returnType);
                beanIntrospector.getPropertyDescriptors().forEach(
                        (name, propertyDescriptor) -> toProperties(properties, propName, result, propertyDescriptor)
                );

            }
        } else {
            properties.put(propName, "");
        }

    }
}
