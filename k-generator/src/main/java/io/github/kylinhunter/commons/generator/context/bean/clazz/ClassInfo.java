package io.github.kylinhunter.commons.generator.context.bean.clazz;

import java.util.List;

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
    private String packageName;
    @EqualsAndHashCode.Include
    private String name;
    private String superClassName;
    private String comment;
    private Interfaces interfaces = new Interfaces();
    private Snippets customSnippets = new Snippets();
    private Imports imports = new Imports();
    private List<FieldMeta> fields = Lists.newArrayList();

    /**
     * @param clazz clazz
     * @return void
     * @title addImport
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:30
     */
    public void addImport(Class<?> clazz) {
        this.imports.add(clazz.getName());
    }

    /**
     * @param packageName packageName
     * @return void
     * @title addImport
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:30
     */
    public void addImport(String packageName) {
        this.imports.add(packageName);
    }

    /**
     * @param fullClassName fullClassName
     * @return void
     * @title addInterface
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:31
     */
    public void addInterface(String fullClassName) {
        this.imports.add(fullClassName);
        this.interfaces.add(fullClassName);
    }

    /**
     * @param fullClassNames fullClassNames
     * @return void
     * @title addInterfaces
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:31
     */

    public void addInterfaces(List<String> fullClassNames) {
        fullClassNames.forEach(this::addInterface);
    }

    /**
     * @param annotation annotation
     * @return void
     * @title addAnnotation
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:31
     */
    public void addClassSnippet(String annotation) {
        this.customSnippets.add(annotation);
    }

    /**
     * @return java.lang.String
     * @title getSuperClassName
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:31
     */
    public String getSuperClassName() {
        return getSuperClassName("extends");
    }

    /**
     * @param prefix prefix
     * @return java.lang.String
     * @title getSuperClassName
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:31
     */
    public String getSuperClassName(String prefix) {
        if (!StringUtils.isEmpty(this.superClassName)) {
            return prefix + " " + this.superClassName;
        }
        return StringUtils.EMPTY;
    }

}
