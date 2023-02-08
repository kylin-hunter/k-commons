package io.github.kylinhunter.commons.yaml;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-03 19:32
 **/
@FunctionalInterface
public interface LoadCorrector {
    /**
     * @title correct
     * @description
     * @author BiJi'an
     * @param text
     * @param yamlType
     * @date 2023-02-09 00:36
     * @return java.lang.String
     */
    String correct(String text, YamlType yamlType);
}
