package io.github.kylinhunter.commons.clazz.agent.plugin.config;

import java.util.List;

import io.github.kylinhunter.commons.collections.ListUtils;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 16:36
 **/
@Data
public class TypeMatchers {
    private List<TypeMatcher> matchers = ListUtils.newArrayList();

}
