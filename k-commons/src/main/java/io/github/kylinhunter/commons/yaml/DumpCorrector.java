package io.github.kylinhunter.commons.yaml;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-03 19:32
 **/
@FunctionalInterface
public interface DumpCorrector {
    /**
     * @param text     text
     * @param yamlType yamlType
     * @return java.lang.String
     * @title correct
     * @description
     * @author BiJi'an
     * @date 2023-02-04 00:36
     */
    String correct(String text, YamlType yamlType);
}
