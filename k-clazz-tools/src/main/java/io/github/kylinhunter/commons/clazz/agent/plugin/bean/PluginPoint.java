package io.github.kylinhunter.commons.clazz.agent.plugin.bean;

import lombok.Data;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 00:47
 */
@Data
public class PluginPoint {
  ElementMatcher<TypeDescription> typeMatcher;
  ElementMatcher<MethodDescription> methodMatcher;
}
