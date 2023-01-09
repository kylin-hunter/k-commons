package io.github.kylinhunter.commons.template.velocity;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolManager;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.template.AbstractTemplateEngine;
import io.github.kylinhunter.commons.template.TemplateExecutor;
import io.github.kylinhunter.commons.template.constant.VelocityConst;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 16:15
 **/
@C
@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class VelocityTemplateEngine extends AbstractTemplateEngine {
    private VelocityEngine velocityEngine;
    private ToolManager toolManager;

    public VelocityTemplateEngine() {

        Properties properties = new Properties();
        properties.setProperty(Velocity.RESOURCE_LOADERS, "class,file");
        properties.setProperty(Velocity.INPUT_ENCODING, Velocity.ENCODING_DEFAULT);

        // resource.loader.class
        properties.setProperty(VelocityConst.KEY_RESOURCE_LOADER_CLASS_CLASS,
                VelocityConst.VALUE_RESOURCE_LOADER_CLASS_CLASS);

        properties.setProperty(VelocityConst.KEY_RESOURCE_LOADER_FILE_CLASS,
                VelocityConst.VALUE_RESOURCE_LOADER_FILE_CLASS);

        // resource.loader.file
        String templatePath = UserDirUtils.getDir("template", true).getAbsolutePath();
        log.info("resource.loader.file.path=>" + templatePath);
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath);

        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "false");
        properties.setProperty("resource.loader.file.modification_check_interval", "0");

        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.init(properties);

        this.toolManager = new ToolManager();
        toolManager.configure("/io/github/kylinhunter/commons/template/velocity-tools.xml");

    }

    /**
     * @return io.github.kylinhunter.commons.templateInfo.context.TemplateExecutor
     * @title createContext
     * @description
     * @author BiJi'an
     * @date 2023-01-05 22:14
     */
    @Override
    public TemplateExecutor createTemplateExecutor() {
        return new VelocityTemplateExecutor(this);
    }

}
