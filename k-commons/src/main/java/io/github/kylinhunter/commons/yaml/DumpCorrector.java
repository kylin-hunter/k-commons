package io.github.kylinhunter.commons.yaml;

import io.github.kylinhunter.commons.name.NameRule;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-03 19:32
 **/
@FunctionalInterface
public interface DumpCorrector {
    /**
     * @param text     text
     * @param nameRule nameRule
     * @return java.lang.String
     * @title correct
     * @description
     * @author BiJi'an
     * @date 2023-02-04 00:36
     */
    String correct(String text, NameRule nameRule);
}
