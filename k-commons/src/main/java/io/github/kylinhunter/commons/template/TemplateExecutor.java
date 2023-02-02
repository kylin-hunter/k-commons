package io.github.kylinhunter.commons.template;

import io.github.kylinhunter.commons.template.bean.Output;
import io.github.kylinhunter.commons.template.bean.OutputBuilder;
import io.github.kylinhunter.commons.template.config.TemplateConfig;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-05 22:01
 **/
public interface TemplateExecutor {

    TemplateConfig getTemplateConfig();

    /**
     * @param key   key
     * @param value value
     * @title put
     * @description
     * @author BiJi'an
     * @date 2023-01-05 22:02
     */
    void putContext(String key, Object value);

    /**
     * @param outputProcessor outputProcessor
     * @return void
     * @title output
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:43
     */
    void output(OutputProcessor outputProcessor);

    /**
     * @return void
     * @title output
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:43
     */
    void output();

    /**
     * @param name      name
     * @param encoding  encoding
     * @param extension extension
     * @return io.github.kylinhunter.commons.template.bean.OutputBuilder
     * @title tmplate
     * @description
     * @author BiJi'an
     * @date 2023-02-03 02:01
     */
    OutputBuilder tmplate(String name, String encoding, String extension);

    /**
     * @param name     name
     * @param encoding encoding
     * @return void
     * @title addTemplate
     * @description
     * @author BiJi'an
     * @date 2023-01-08 22:56
     */
    OutputBuilder tmplate(String name, String encoding);

    /**
     * @param name name
     * @return io.github.kylinhunter.commons.templateInfo.bean.OutputBuilder
     * @title tmplate
     * @description
     * @author BiJi'an
     * @date 2023-01-08 23:00
     */
    OutputBuilder tmplate(String name);

    /**
     * @param output output
     * @return void
     * @title add
     * @description
     * @author BiJi'an
     * @date 2023-01-08 02:08
     */
    void addOutput(Output output);

}
