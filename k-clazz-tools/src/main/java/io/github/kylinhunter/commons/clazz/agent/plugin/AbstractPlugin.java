package io.github.kylinhunter.commons.clazz.agent.plugin;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.clazz.agent.plugin.config.PluginConfig;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.PointCut;
import io.github.kylinhunter.commons.clazz.agent.plugin.config.TypeMatcher;
import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.ListUtils;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.description.NamedElement;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-13 19:15
 **/
@Data
@RequiredArgsConstructor
public abstract class AbstractPlugin<T extends PluginConfig, V extends
        AgentTransformer> implements Plugin<T, V> {

    private final String name;

    private T config;
    private Class<V> transformer;

    @Override
    public Class<V> getTransformer() {
        return transformer;
    }

    private List<PluginPoint> pluginPoints = ListUtils.newArrayList();

    public void buildPluginPoint() {
        List<PointCut> pointCuts = config.getPointCuts();
        if (!CollectionUtils.isEmpty(pointCuts)) {
            for (PointCut pointCut : pointCuts) {
                PluginPoint pluginPoint = new PluginPoint();
                pluginPoint.setTypeMatcher(toElementMatcher(pointCut.getTypeMatcher()));
                pluginPoint.setMethodMatcher(toElementMatcher(pointCut.getMethodMatcher()));
                this.pluginPoints.add(pluginPoint);
            }
        }
    }

    public <S extends NamedElement> ElementMatcher<S> toElementMatcher(TypeMatcher typeMatcher) {
        String nameStartsWith = typeMatcher.getNameStartsWith();
        ElementMatcher.Junction<S> elementMatcherJunction = null;
        if (!StringUtils.isEmpty(nameStartsWith)) {
            elementMatcherJunction = ElementMatchers.nameStartsWith(nameStartsWith);
        }

        String nameContains = typeMatcher.getNameContains();
        if (!StringUtils.isEmpty(nameContains)) {
            if (elementMatcherJunction == null) {
                elementMatcherJunction = ElementMatchers.nameContains(nameContains);
            } else {
                elementMatcherJunction = elementMatcherJunction.or(ElementMatchers.nameContains(nameContains));

            }
        }

        String nameRegex = typeMatcher.getNameRegex();
        if (!StringUtils.isEmpty(nameRegex)) {
            if (elementMatcherJunction == null) {
                elementMatcherJunction = ElementMatchers.nameMatches(nameContains);
            } else {
                elementMatcherJunction = elementMatcherJunction.or(ElementMatchers.nameMatches(nameContains));

            }
        }
        return elementMatcherJunction;
    }

    public void init() {
        this.buildPluginPoint();
    }

}
