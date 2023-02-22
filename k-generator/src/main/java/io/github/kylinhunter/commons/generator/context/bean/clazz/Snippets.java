package io.github.kylinhunter.commons.generator.context.bean.clazz;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import io.github.kylinhunter.commons.generator.constant.CodeLocation;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 17:17
 **/
@Data
public class Snippets {
    private Map<String, List<String>> snippets = Maps.newHashMap();

    /**
     * @param location location
     * @return java.lang.String
     * @title toString
     * @description
     * @author BiJi'an
     * @date 2023-02-19 00:42
     */
    public String toString(String location) {
        return toString(location, System.lineSeparator());
    }

    /**
     * @param separator separator
     * @return java.lang.String
     * @title toString
     * @description
     * @author BiJi'an
     * @date 2023-02-19 17:20
     */
    public String toString(String location, String separator) {
        List<String> tmpSnippets = this.snippets.get(location);
        if (!CollectionUtils.isEmpty(tmpSnippets)) {
            return String.join(separator, tmpSnippets);

        }
        return StringUtils.EMPTY;
    }

    /**
     * @param snippets snippets
     * @return void
     * @title add
     * @description
     * @author BiJi'an
     * @date 2023-02-19 17:24
     */
    public void add(String location, String snippets) {

        switch (location) {
            case CodeLocation.CLASS_BEFORE:
                this.compute(CodeLocation.CLASS_BEFORE, snippets);
                break;
            case CodeLocation.FIELD_BEFORE:
                this.compute(CodeLocation.FIELD_BEFORE, snippets);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + location);
        }

    }

    /**
     * @param location location
     * @param snippets snippets
     * @return void
     * @title compute
     * @description
     * @author BiJi'an
     * @date 2023-02-19 00:35
     */
    private void compute(String location, String snippets) {
        this.snippets.compute(location, (k, v) -> {

                    if (v == null) {
                        v = Lists.newArrayList();
                    }
                    v.add(snippets);

                    return v;
                }

        );

    }
}
