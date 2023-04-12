package io.github.kylinhunter.commons.generator.template.config;

public interface ConfigCustomize<T> {
    /**
     * @param config config
     * @return void
     * @title Customize Configuration
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:15
     */
    void customize(T config);
}
