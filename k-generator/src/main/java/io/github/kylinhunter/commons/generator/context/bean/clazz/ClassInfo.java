package io.github.kylinhunter.commons.generator.context.bean.clazz;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

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
    private SuperClass superClass = new SuperClass();
    private String comment;
    private Interfaces interfaces = new Interfaces();
    private Snippets snippets = new Snippets();
    private Imports imports = new Imports();
    private List<FieldInfo> fields = Lists.newArrayList();

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
     * @param fullClassName fullClassName
     * @return void
     * @title addImport
     * @description
     * @author BiJi'an
     * @date 2023-02-19 19:30
     */
    public void addImport(String fullClassName) {
        this.imports.add(fullClassName);
    }

    /**
     * @param fullClassName className
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
        if (!CollectionUtils.isEmpty(fullClassNames)) {
            fullClassNames.forEach(this::addInterface);
        }
    }

    /**
     * @param location location
     * @param snippets snippets
     * @return void
     * @title add
     * @description
     * @author BiJi'an
     * @date 2023-02-19 00:39
     */
    public void addSnippet(String location, String snippets) {
        this.snippets.add(location, snippets);
    }

    /**
     * @param fullClassNames fullClassNames
     * @return void
     * @title setSuperClass
     * @description
     * @author BiJi'an
     * @date 2023-02-19 23:56
     */
    public void setSuperClass(String fullClassNames) {
        this.superClass.setClassName(fullClassNames);
        this.imports.add(fullClassNames);
    }

    /**
     * @param prefix prefix
     * @return java.lang.String
     * @title superClass
     * @description
     * @author BiJi'an
     * @date 2023-02-19 23:56
     */
    public String superClass(String prefix) {
        return this.superClass.toString(prefix);
    }

    /**
     * @param prefix prefix
     * @return java.lang.String
     * @title interfaces
     * @description
     * @author BiJi'an
     * @date 2023-02-19 23:57
     */
    public String interfaces(String prefix) {
        return this.interfaces.toString(prefix);
    }

    /**
     * @param prefix  prefix
     * @param postfix postfix
     * @return java.lang.String
     * @title imports
     * @description
     * @author BiJi'an
     * @date 2023-02-19 00:03
     */
    public String imports(String prefix, String postfix) {
        return this.imports.toString(prefix, postfix);
    }


    /**
     * @title snippets
     * @description
     * @author BiJi'an
     * @param location location
     * @date 2023-02-19 00:48
     * @return java.lang.String
     */
    public String snippets(String location){
        return this.snippets.toString(location);
    }
}
