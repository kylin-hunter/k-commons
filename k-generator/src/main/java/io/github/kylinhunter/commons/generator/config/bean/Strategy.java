package io.github.kylinhunter.commons.generator.config.bean;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ClassUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Data;

@Data
public class Strategy {
    private String packagePattern;
    private String name;
    private Map<String, String> variables = Maps.newHashMap();
    private boolean lombok = false;
    private boolean lombokChainModel = false;
    private String superClass;
    private String superClassName;
    private boolean serializable = false;
    private List<String> skipFields = Lists.newArrayList();

    public void addSkipField(String skipField) {
        skipFields.add(skipField);
    }

    /**
     * @param superClass 父类
     * @return com.baidu.icpd.cskb.generator.custom.configuration.Strategy
     * @throws
     * @title 设置父类
     * @description
     * @author BiJi'an
     * @updateTime 2021/8/4 4:49 下午
     */
    public Strategy setSuperClass(Class<?> superClass) {

        this.superClass = superClass.getCanonicalName();
        this.superClassName = ClassUtils.getShortClassName(superClass);
        return this;
    }

    /**
     * @param superClass
     * @return com.baidu.icpd.cskb.generator.custom.configuration.Strategy
     * @throws
     * @title 设置父类
     * @description
     * @author BiJi'an
     * @updateTime 2021/8/5 9:47 下午
     */

    public Strategy setSuperClass(String superClass) {
        this.superClass = superClass;
        this.superClassName = ClassUtils.getShortClassName(superClass);
        return this;
    }

}
