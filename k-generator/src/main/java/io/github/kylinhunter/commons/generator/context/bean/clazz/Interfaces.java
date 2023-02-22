package io.github.kylinhunter.commons.generator.context.bean.clazz;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;

import com.google.common.collect.Lists;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 17:17
 **/
public class Interfaces {
    private final List<String> shortClassNames = Lists.newArrayList();
    private final List<String> fullClassNames = Lists.newArrayList();

    @Override
    public String toString() {
        return "implements " + shortClassNames.stream().collect(Collectors.joining(","));
    }

    /**
     * @param prefix prefix
     * @return java.lang.String
     * @title toString
     * @description
     * @author BiJi'an
     * @date 2023-02-19 17:20
     */
    public String toString(String prefix) {
        return prefix + String.join(",", shortClassNames);
    }

    /**
     * @param fullClassName fullClassName
     * @return void
     * @title add
     * @description
     * @author BiJi'an
     * @date 2023-02-19 17:24
     */
    public void add(String fullClassName) {
        this.fullClassNames.add(fullClassName);
        this.shortClassNames.add(ClassUtils.getShortClassName(fullClassName));

    }
}
