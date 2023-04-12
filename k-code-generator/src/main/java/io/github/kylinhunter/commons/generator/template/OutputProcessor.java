package io.github.kylinhunter.commons.generator.template;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-06 01:34
 **/
@FunctionalInterface
public interface OutputProcessor {
    void process(String text);
}
