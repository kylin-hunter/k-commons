package io.github.kylinhunter.commons.clazz.agent.plugin.config;

import java.util.List;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:28
 **/
@Data
public class PluginConfig {
    private String name;
    private List<TypeMatcher> packageMatchers;
    private List<TypeMatcher> methodMatchers;
}
