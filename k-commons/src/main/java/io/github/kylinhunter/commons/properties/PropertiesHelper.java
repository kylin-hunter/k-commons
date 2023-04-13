package io.github.kylinhunter.commons.properties;

import static java.lang.reflect.Array.newInstance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.bean.info.BeanIntrospector;
import io.github.kylinhunter.commons.bean.info.BeanIntrospectors;
import io.github.kylinhunter.commons.bean.info.ExPropType;
import io.github.kylinhunter.commons.bean.info.ExPropertyDescriptor;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.date.DateUtils;
import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.exception.embed.GeneralException;
import io.github.kylinhunter.commons.exception.embed.KIOException;
import io.github.kylinhunter.commons.io.Charsets;
import io.github.kylinhunter.commons.io.ResourceHelper;
import io.github.kylinhunter.commons.name.NameRule;
import io.github.kylinhunter.commons.reflect.ObjectCreator;
import io.github.kylinhunter.commons.reflect.ReflectUtils;
import io.github.kylinhunter.commons.util.ObjectValues;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 11:30
 **/
public class PropertiesHelper {

    private static KeyCorrector defaultKeyCorrector = new DefaultKeyCorrector();
    private static final int MAX_LEVEL = 1000;

    /**
     * @param keyCorrector keyCorrector
     * @return void
     * @title resetKeyCorrector
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:39
     */
    @SuppressWarnings("unused")
    public static void resetDefaultKeyCorrector(KeyCorrector keyCorrector) {
        defaultKeyCorrector = keyCorrector;
    }

    /**
     * @param path path
     * @return java.util.Properties
     * @title load
     * @description
     * @author BiJi'an
     * @date 2023-03-19 01:28
     */
    public static Properties load(String path) {
        return load(path, "");
    }

    /**
     * @param path    path
     * @param charset charset
     * @return java.util.Properties
     * @title load
     * @description
     * @author BiJi'an
     * @date 2023-03-19 23:20
     */
    public static Properties load(String path, String charset) {
        Properties properties = new Properties();
        try (InputStream inputStream = ResourceHelper.getInputStream(path, ResourceHelper.PathType.FILESYSTEM, true);
             InputStreamReader read = new InputStreamReader(inputStream, Charsets.toCharset(charset))) {
            properties.load(read);
        } catch (IOException e) {
            throw new KIOException("load properties error", e);
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
     * @date 2023-03-19 00:01
     */
    public static <T> T load(String path, Class<T> clazz) {
        return load(path, null, null, clazz);

    }

    /**
     * @param path     path
     * @param nameRule nameRule
     * @param clazz    clazz
     * @return T
     * @title load
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:01
     */
    public static <T> T load(String path, NameRule nameRule, Class<T> clazz) {
        return load(path, null, nameRule, clazz);

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
    public static <T> T load(String path, String charset, NameRule nameRule, Class<T> clazz) {
        Properties properties = load(path, charset);
        return toBean(properties, nameRule, clazz);

    }

    /**
     * @param properties properties
     * @param clazz      clazz
     * @return T
     * @title toBean
     * @description
     * @author BiJi'an
     * @date 2023-03-19 00:00
     */
    public static <T> T toBean(Properties properties, Class<T> clazz) {
        return toBean(properties, null, clazz);
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
    @SuppressWarnings("unchecked")
    public static <T> T toBean(Properties properties, NameRule nameRule, Class<T> clazz) {
        if (nameRule != null) {
            Properties newProperties = new Properties();
            properties.forEach((k, v) -> newProperties.put(defaultKeyCorrector.correct(k, nameRule), v));
            properties = newProperties;
        }
        ExceptionChecker.checkNotNull(clazz);
        PropObjectPool objectPool = createPropObjectPool(properties, clazz);
        List<PropObject> propObjects = objectPool.getSortedPropObjes(); // resort
        propObjects.forEach(propObject -> {
            propObject.fieldResort();
            processPropFileds(objectPool, propObject);
        });

        return (T) objectPool.getRoot().getObj();
    }

    private static <T> PropObjectPool createPropObjectPool(Properties properties, Class<T> clazz) {
        PropObjectPool propObjectPool = new PropObjectPool();

        // add root object
        propObjectPool.add(new PropObject(PropObject.ROOT_ID, ObjectCreator.create(clazz)));

        // process all propObject
        properties.forEach((name, propValue) -> {
            String fullName = (String) name;
            PropFiled propFiled = new PropFiled(fullName, propValue);
            PropObject curObject = propObjectPool.get(propFiled.getObjecId());
            if (curObject == null) {
                curObject = createPropObject(propObjectPool, propFiled);
            }
            curObject.addField(propFiled);
        });

        return propObjectPool;
    }

    /**
     * @param objectPool objectPool
     * @param propFiled  propFiled
     * @return io.github.kylinhunter.commons.properties.PropObject
     * @title getPropObject
     * @description
     * @author BiJi'an
     * @date 2023-03-31 01:34
     */
    private static PropObject createPropObject(PropObjectPool objectPool, PropFiled propFiled) {
        PropObject curObject = objectPool.add(new PropObject(propFiled.getObjecId()));
        if (!curObject.isRoot()) {
            String parentId = curObject.getParentId();
            PropObject parentObj = objectPool.get(parentId);
            PropObject curObj = curObject;
            int level = 0;
            while (parentObj == null) {
                if (++level > MAX_LEVEL) {
                    throw new GeneralException("create too many level one time , > " + MAX_LEVEL);
                }
                PropObject newParentObject = objectPool.add(new PropObject(curObj.parentId));
                PropFiled nowField = new PropFiled(curObj.getObjecId());
                newParentObject.addField(nowField);
                curObj = newParentObject;
                parentObj = objectPool.get(curObj.parentId);
            }
            parentObj.addField(new PropFiled(curObj.getObjecId()));
        }
        return curObject;

    }

    /**
     * @param objectPool    objectPool
     * @param curPropObject curPropObject
     * @return void
     * @title processPropFileds
     * @description
     * @author BiJi'an
     * @date 2023-04-06 20:42
     */
    private static void processPropFileds(PropObjectPool objectPool, PropObject curPropObject) {
        BeanIntrospector beanIntrospector = curPropObject.getBeanIntrospector();
        Objects.requireNonNull(beanIntrospector);
        Object curObj = curPropObject.getObj();
        for (PropFiled propFiled : curPropObject.getPropFileds()) {

            ExPropertyDescriptor propertyDescriptor = beanIntrospector.getExPropertyDescriptor(propFiled.getName());
            Objects.requireNonNull(propertyDescriptor, propFiled.getName() + " can't be null");

            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();
            Class<?> propActualClazz = propertyDescriptor.getActualClazz();
            Class<?> propertyType = propertyDescriptor.getPropertyType();
            ExPropType exPropType = propertyDescriptor.getExPropType();

            if (exPropType == ExPropType.PRIMITIVE_OR_WRAPPER) {
                Object o = ObjectValues.get(propFiled.getValue(), propertyType);
                ReflectUtils.invoke(curObj, writeMethod, o);

            } else if (propertyType == String.class) {
                ReflectUtils.invoke(curObj, writeMethod, ObjectValues.getString(propFiled.getValue()));

            } else {
                PropObject fullNamePropObject = objectPool.get(propFiled.getFullName());
                Objects.requireNonNull(fullNamePropObject, "can't find full name Obj: " + propFiled.getFullName());

                if (exPropType == ExPropType.ARRAY) {

                    if (propFiled.arrIndex >= 0) {
                        int arrLen = propFiled.arrIndex + 1;
                        Object[] arr = ReflectUtils.invoke(curObj, readMethod);
                        if (arr == null) {
                            arr = (Object[]) newInstance(propActualClazz, arrLen);
                        } else {
                            arr = Arrays.copyOf(arr, arrLen);
                        }
                        Object[] params = new Object[] {arr};
                        ReflectUtils.invoke(curObj, writeMethod, params);
                        Object newObj = ObjectCreator.create(propActualClazz);
                        arr[propFiled.arrIndex] = newObj;

                        fullNamePropObject.setObj(newObj);

                    }

                } else if (exPropType == ExPropType.LIST) {
                    if (propFiled.arrIndex >= 0) {
                        int listSize = propFiled.arrIndex + 1;

                        List<Object> list = ReflectUtils.invoke(curObj, readMethod);
                        if (list == null) {
                            list = ListUtils.newArrayList();
                        } else {
                            List<Object> newList = ListUtils.newArrayList();
                            newList.addAll(list);
                            list = newList;
                        }
                        for (int i = list.size(); i < listSize; i++) {

                            list.add(null);
                        }
                        ReflectUtils.invoke(curObj, writeMethod, list);

                        Object newObj = ObjectCreator.create(propActualClazz);
                        list.set(propFiled.arrIndex, newObj);
                        fullNamePropObject.setObj(newObj);
                    }

                } else if (exPropType == ExPropType.NON_JDK_TYPE) {
                    if (propFiled.isBean()) {
                        Object o = ObjectCreator.create(propertyType);
                        ReflectUtils.invoke(curObj, writeMethod, o);
                        fullNamePropObject.setObj(o);
                    }

                }
            }

        }
    }

    public static Properties store(Object obj, File file) {
        return store(obj, null, file);
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
    public static Properties store(Object obj, NameRule nameRule, File file) {
        return store(obj, nameRule, file, null);
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
    public static Properties store(Object obj, NameRule nameRule, File file, String charset) {
        return store(toProperties(obj, nameRule), file, charset);
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
        try (FileOutputStream out = new FileOutputStream(file);
             OutputStreamWriter fileWriter = new OutputStreamWriter(out, Charsets.toCharset(charset))) {
            properties.store(fileWriter, "auto create properties at " + DateUtils.formatNow());
            return properties;
        } catch (Exception e) {
            throw new KIOException("store  error", e);
        }

    }

    public static Properties toProperties(Object obj) {
        return toProperties(obj, null);
    }

    /**
     * @param obj obj
     * @return java.util.Properties
     * @title toProperties
     * @description
     * @author BiJi'an
     * @date 2023-03-19 01:06
     */
    public static Properties toProperties(Object obj, NameRule nameRule) {
        Properties properties = new Properties();
        BeanIntrospector beanIntrospector = BeanIntrospectors.get(obj.getClass());
        Map<String, ExPropertyDescriptor> pds = beanIntrospector.getExPropertyDescriptors();
        for (Map.Entry<String, ExPropertyDescriptor> entry : pds.entrySet()) {
            ExPropertyDescriptor propertyDescriptor = entry.getValue();
            toProperties(properties, "", obj, propertyDescriptor);
        }
        if (nameRule != null) {
            Properties newProperties = new Properties();
            properties.forEach((k, v) -> newProperties.put(defaultKeyCorrector.correct(k, nameRule), v));
            return newProperties;
        }
        return properties;
    }

    /**
     * @param properties properties
     * @param parent     parent
     * @param obj        obj
     * @param exPd       exPd
     * @return void
     * @title toProperties
     * @description
     * @author BiJi'an
     * @date 2023-03-19 23:21
     */
    private static void toProperties(Properties properties, String parent, Object obj, ExPropertyDescriptor exPd) {

        if (!exPd.isCanReadWrite()) {
            return;
        }
        if (!StringUtils.isEmpty(parent)) {
            parent += ".";
        }

        ExPropType type = exPd.getExPropType();
        String propName = parent + exPd.getName();

        Method readMethod = exPd.getReadMethod();
        Object result = ReflectUtils.invoke(obj, readMethod);
        Class<?> returnType = readMethod.getReturnType();
        if (result == null) {
            return;
        }

        // add sub type
        if (type == ExPropType.PRIMITIVE_OR_WRAPPER) {
            properties.put(propName, String.valueOf(result));

        } else if (returnType == String.class) {
            if (!StringUtils.isEmpty((String) result)) {
                properties.put(propName, result);
            }
        } else if (type == ExPropType.LIST) {
            Class<?> propActualClazz = exPd.getActualClazz();
            BeanIntrospector beanIntrospector = BeanIntrospectors.get(propActualClazz);

            List<?> objs = (List<?>) result;
            for (int i = 0; i < objs.size(); i++) {
                Map<String, ExPropertyDescriptor> exPropertyDescriptors =
                        beanIntrospector.getExPropertyDescriptors();
                for (Map.Entry<String, ExPropertyDescriptor> entry : exPropertyDescriptors.entrySet()) {
                    ExPropertyDescriptor propertyDescriptor = entry.getValue();
                    toProperties(properties, propName + "[" + i + "]", objs.get(i), propertyDescriptor);

                }
            }
        } else if (type == ExPropType.ARRAY) {
            Class<?> propActualClazz = exPd.getActualClazz();
            BeanIntrospector beanIntrospector = BeanIntrospectors.get(propActualClazz);

            Object[] objs = (Object[]) result;
            for (int i = 0; i < objs.length; i++) {
                Map<String, ExPropertyDescriptor> exPropertyDescriptors =
                        beanIntrospector.getExPropertyDescriptors();
                for (Map.Entry<String, ExPropertyDescriptor> entry : exPropertyDescriptors.entrySet()) {
                    ExPropertyDescriptor propertyDescriptor = entry.getValue();
                    toProperties(properties, propName + "[" + i + "]", objs[i], propertyDescriptor);

                }
            }
        } else {
            BeanIntrospector beanIntrospector = BeanIntrospectors.get(returnType);
            beanIntrospector.getExPropertyDescriptors().forEach(
                    (name, propertyDescriptor) -> toProperties(properties, propName, result, propertyDescriptor)
            );

        }
    }

}
