package io.github.kylinhunter.commons.clazz.agent.plugin.config.bean;

import io.github.kylinhunter.commons.collections.ListUtils;
import java.util.List;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:28
 **/
@Data
public class PluginConfig {
    private List<PointCut> points = ListUtils.newArrayList();
    private DebugConfig debug;
}
