package io.github.kylinhunter.commons.template.velocity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.ToolContext;

import io.github.kylinhunter.commons.template.AfterOutputProcessor;
import io.github.kylinhunter.commons.template.TemplateBuilder;
import io.github.kylinhunter.commons.template.bean.TemplateOutput;
import io.github.kylinhunter.commons.template.bean.TemplateOutputs;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 22:01
 **/
@Data
public class VelocityTemplateBuilder implements TemplateBuilder {

    private VelocityEngine velocityEngine;
    private ToolContext toolContext;
    private TemplateOutputs templateOutputs = new TemplateOutputs();

    public VelocityTemplateBuilder(VelocityTemplateEngine velocityEngine) {
        this.velocityEngine = velocityEngine.getVelocityEngine();
        this.toolContext = velocityEngine.getToolManager().createContext();
        this.templateOutputs = new TemplateOutputs(velocityEngine.getGlobalConfig());
    }

    /**
     * @param key   key
     * @param value value
     * @return java.lang.Object
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-01-05 22:02
     */
    @Override
    public Object putContext(String key, Object value) {
        return this.toolContext.put(key, value);
    }

    /**
     * @return void
     * @title output
     * @description
     * @author BiJi'an
     * @date 2023-01-05 22:15
     */
    public void output(AfterOutputProcessor afterOutputProcessor) {
        try {
            for (TemplateOutput templateOutput : templateOutputs.getDatas()) {
                String name = templateOutput.getName();
                String encoding = templateOutput.getEncoding();
                File file = templateOutput.getFile();

                Template template = !StringUtils.isEmpty(encoding) ?
                        this.velocityEngine.getTemplate(name, encoding) : this.velocityEngine.getTemplate(name);

                StringWriter sw = new StringWriter();
                template.merge(toolContext, sw);
                String resultText = sw.toString();
                if (afterOutputProcessor != null) {
                    afterOutputProcessor.accept(resultText);
                }
                if (file != null) {
                    try (FileWriter fileWriter = new FileWriter(file);) {
                        fileWriter.write(resultText);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void output() {
        this.output(null);
    }
}
