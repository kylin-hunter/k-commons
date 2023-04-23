package io.github.kylinhunter.commons.clazz.agent.plugin.config.bean;

import java.io.File;

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
    private boolean classSaveDirAutoClean = true;
    private File fileClassSaveDir;
}
