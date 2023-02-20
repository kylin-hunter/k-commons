package io.github.kylinhunter.commons.generator.context.bean;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<String> allImportPackages = Lists.newArrayList();
    private List<String> allInterfaces = Lists.newArrayList();
    private List<String> allAnnotations = Lists.newArrayList();

    public void addImportPackage(Class<?> clazz) {
        this.addImportPackage(clazz.getName());
    }

    public void addImportPackage(String importPackage) {
        this.allImportPackages.add(importPackage);
    }

    public void addInterface(Class<?> clazz) {
        this.addInterface(clazz.getSimpleName());
        this.addImportPackage(clazz);
    }

    public void addInterface(String i) {
        this.allInterfaces.add(i);
    }

    public void addAnnotation(Class<? extends Annotation> annotation) {
        this.addAnnotation(annotation.getSimpleName());
        this.addImportPackage(annotation);
    }

    public void addAnnotation(String annotation) {
        this.allAnnotations.add(annotation);
    }

    public String getImportPackages() {
        return allImportPackages.stream().map(e -> "import " + e + ";")
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String getInterfaces() {
        return "implements " + allInterfaces.stream().collect(Collectors.joining(","));
    }

    public String getAnnotations() {
        return allAnnotations.stream().map(e -> "@" + e).collect(Collectors.joining(System.lineSeparator()));
    }

    public String getSuperClassName() {
        if (!StringUtils.isEmpty(this.superClassName)) {
            return "extends " + this.superClassName;

        }
        return "";
    }

}
