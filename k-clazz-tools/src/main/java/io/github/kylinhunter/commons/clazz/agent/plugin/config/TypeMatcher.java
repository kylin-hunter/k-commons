package io.github.kylinhunter.commons.clazz.agent.plugin.config;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 16:36
 **/
@Data
public class TypeMatcher {
    private String startsWith;
    private String contains;
    private String regex;

}
