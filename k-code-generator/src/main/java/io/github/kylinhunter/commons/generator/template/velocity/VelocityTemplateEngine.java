package io.github.kylinhunter.commons.generator.template.velocity;

import java.io.File;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolManager;

import io.github.kylinhunter.commons.generator.template.AbstractTemplateEngine;
import io.github.kylinhunter.commons.generator.template.TemplateExecutor;
import io.github.kylinhunter.commons.generator.template.config.OutputConfig;
import io.github.kylinhunter.commons.generator.template.constant.VelocityConst;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 16:15
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Slf4j
public class VelocityTemplateEngine extends AbstractTemplateEngine {
    private VelocityEngine velocityEngine;
    private ToolManager toolManager;

    public VelocityTemplateEngine() {
        reInit();
    }

    /**
     * @return void
     * @title reInit
     * @description
     * @author BiJi'an
     * @date 2023-02-03 01:27
     */
    protected void reInit() {
        Properties properties = new Properties();
        properties.setProperty(Velocity.RESOURCE_LOADERS, "class,file");
        properties.setProperty(Velocity.INPUT_ENCODING, Velocity.ENCODING_DEFAULT);

        // resource.loader.class
        properties.setProperty(VelocityConst.KEY_RESOURCE_LOADER_CLASS_CLASS,
                VelocityConst.VALUE_RESOURCE_LOADER_CLASS_CLASS);

        properties.setProperty(VelocityConst.KEY_RESOURCE_LOADER_FILE_CLASS,
                VelocityConst.VALUE_RESOURCE_LOADER_FILE_CLASS);

        // resource.loader.file
        String templatePath = templateConfig.getTemplatePath().toFile().getAbsolutePath();
        log.info("resource.loader.file.path=>" + templatePath);
        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath);

        properties.setProperty(Velocity.FILE_RESOURCE_LOADER_CACHE, "true");
        properties.setProperty("resource.loader.file.modification_check_interval", "60");

        this.velocityEngine = new VelocityEngine();
        this.velocityEngine.init(properties);

        this.toolManager = new ToolManager();
        this.toolManager.configure("/io/github/kylinhunter/commons/template/velocity-tools.xml");


        if(this.templateConfig.getOutputConfig().isAutoClean()){
            OutputConfig outputConfig = templateConfig.getOutputConfig();
            for (File file : Objects.requireNonNull(outputConfig.getOutputPath().toFile().listFiles())) {
                log.info("delete file=>" + file.getAbsolutePath());
                FileUtils.deleteQuietly(file);
            }
        }
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

    @Override
    public TemplateExecutor createTemplateExecutor(Map<String, Object> context) {
        return new VelocityTemplateExecutor(this, context);

    }

}
