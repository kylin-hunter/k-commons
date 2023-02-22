package io.github.kylinhunter.commons.generator.context.bean.clazz;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 17:17
 **/
@Data
public class Snippets {
    private List<String> snippets = Lists.newArrayList();

    @Override
    public String toString() {
        return toString(System.lineSeparator());
    }

    /**
     * @param separator separator
     * @return java.lang.String
     * @title toString
     * @description
     * @author BiJi'an
     * @date 2023-02-19 17:20
     */
    public String toString(String separator) {
        return String.join(separator, snippets);
    }

    /**
     * @param snippets snippets
     * @return void
     * @title add
     * @description
     * @author BiJi'an
     * @date 2023-02-19 17:24
     */
    public void add(String snippets) {
        this.snippets.add(snippets);

    }
}
