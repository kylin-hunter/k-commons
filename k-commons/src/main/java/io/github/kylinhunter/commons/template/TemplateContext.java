package io.github.kylinhunter.commons.template;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 22:01
 **/
public interface TemplateContext {

    /**
     * @param key   key
     * @param value value
     * @return java.lang.Object
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-01-05 22:02
     */
    Object put(String key, Object value);

}
