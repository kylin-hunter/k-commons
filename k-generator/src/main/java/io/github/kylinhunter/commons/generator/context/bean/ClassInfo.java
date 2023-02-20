package io.github.kylinhunter.commons.generator.context.bean;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.stream.Collectors;

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
    private String className;
    private String packageName;
    private String superClass;

    private List<String> allImportPackages = Lists.newArrayList();
    private List<String> interfaces = Lists.newArrayList();
    private List<String> allAnnotations = Lists.newArrayList();




    public void addImportPackage(String importPackage) {
        this.allImportPackages.add("import " + importPackage + ";");
    }

    public void addImportPackage(Class<?> clazz) {
        this.addImportPackage(clazz.getName());
    }

    public void addAnnotation(Class<? extends Annotation> annotation) {
        this.addImportPackage(annotation.getName());
        this.addAnnotation(annotation.getSimpleName());
    }

    public void addAnnotation(String annotation) {
        this.allAnnotations.add("@" + annotation);
    }

    public String getImportPackages1() {
        return allImportPackages.stream().collect(Collectors.joining(System.lineSeparator()));
    }

}
