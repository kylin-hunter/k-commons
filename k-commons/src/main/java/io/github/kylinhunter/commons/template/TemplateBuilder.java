package io.github.kylinhunter.commons.template;

import java.io.File;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 22:01
 **/
public interface TemplateBuilder<T> {

    /**
     * @param key   key
     * @param value value
     * @return java.lang.Object
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-01-05 22:02
     */
    Object putContext(String key, Object value);

    /**
     * @param name     name
     * @param encoding encoding
     * @return void
     * @title add
     * @description
     * @author BiJi'an
     * @date 2023-01-05 23:12
     */
    void addTemplate(String name, String encoding, File file);

    /**
     * @param name name
     * @return void
     * @title add
     * @description
     * @author BiJi'an
     * @date 2023-01-05 23:12
     */
    void addTemplate(String name, File file);

    void output(AfterOutputProcessor afterOutputProcessor);

    void output();

}
