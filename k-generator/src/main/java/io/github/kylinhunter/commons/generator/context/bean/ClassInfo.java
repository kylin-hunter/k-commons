package io.github.kylinhunter.commons.generator.context.bean;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 20:39
 **/
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClassInfo {
    @EqualsAndHashCode.Include
    private String name;

    private String packageName;
    private String superClassName;
    private String remarks;

    private List<String> importPackages = Lists.newArrayList();
    private List<String> interfaces = Lists.newArrayList();

    public void addImportPackage(Class<?> clazz) {
        this.addImportPackage(clazz.getName());
    }

    public void addImportPackage(String importPackage) {
        this.importPackages.add(importPackage);
    }

    public void addInterfaces(List<String> fullClassNames) {
        fullClassNames.forEach(fullClassName -> {
            this.addInterface(fullClassName);
        });
    }

    public void addInterface(String fullClassName) {
        this.importPackages.add(fullClassName);
        this.interfaces.add(ClassUtils.getShortClassName(fullClassName));

    }

    public String getInterfaces() {
        return "implements " + interfaces.stream().collect(Collectors.joining(","));
    }

    public String getSuperClassName() {
        if (!StringUtils.isEmpty(this.superClassName)) {
            return "extends " + this.superClassName;

        }
        return "";
    }

}
