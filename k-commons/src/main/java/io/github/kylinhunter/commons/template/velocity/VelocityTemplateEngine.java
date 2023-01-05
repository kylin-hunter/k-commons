package io.github.kylinhunter.commons.template.velocity;

import java.util.Properties;

import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolManager;

import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.io.file.UserDirUtils;
import io.github.kylinhunter.commons.template.AbstractTemplateEngine;
import io.github.kylinhunter.commons.template.TemplateBuilder;
import io.github.kylinhunter.commons.template.constant.VelocityConst;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 16:15
 **/
@C
@Data
public class VelocityTemplateEngine extends AbstractTemplateEngine {
    private VelocityEngine velocityEngine;
    private ToolManager toolManager;

    public VelocityTemplateEngine() {

        Properties properties = new Properties();
        properties.setProperty(Velocity.RESOURCE_LOADERS, "class,file");
        properties.setProperty(VelocityConst.KEY_RESOURCE_LOADER_CLASS_CLASS,
                VelocityConst.VALUE_RESOURCE_LOADER_CLASS_CLASS);

        properties.setProperty(VelocityConst.KEY_RESOURCE_LOADER_FILE_CLASS,
                VelocityConst.VALUE_RESOURCE_LOADER_FILE_CLASS);

        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH,
                UserDirUtils.getDir("template", true).getAbsolutePath());

        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "false");
        properties.setProperty("resource.loader.file.modification_check_interval", "0");
        properties.setProperty(Velocity.INPUT_ENCODING, Velocity.ENCODING_DEFAULT);
        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.init(properties);

        this.toolManager = new ToolManager();
        toolManager.configure("/io/github/kylinhunter/commons/template/velocity-tools.xml");

    }

    /**
     * @return io.github.kylinhunter.commons.template.context.TemplateBuilder
     * @title createContext
     * @description
     * @author BiJi'an
     * @date 2023-01-05 22:14
     */
    @Override
    public TemplateBuilder createTemplateBuilder() {
        return new VelocityTemplateBuilder(this);
    }

}
