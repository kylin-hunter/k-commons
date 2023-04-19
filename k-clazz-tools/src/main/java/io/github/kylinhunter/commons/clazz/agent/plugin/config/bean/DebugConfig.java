package io.github.kylinhunter.commons.clazz.agent.plugin.config.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:28
 **/
@Data
public class DebugConfig {
    private boolean enabled;
    private String classSaveDir;
}
