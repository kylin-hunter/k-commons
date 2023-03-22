package io.github.kylinhunter.commons.clazz.agent.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.ListUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 23:46
 **/
public class AgentArgs {
    public static final String CONFIG_FILE = "config-file";
    private final Map<String, List<String>> configs = new HashMap<>();

    /**
     * @param name  name
     * @param value value
     * @return void
     * @title add
     * @description
     * @author BiJi'an
     * @date 2023-03-19 11:02
     */
    public void add(String name, String value) {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(value)) {
            configs.compute(name.toLowerCase(), (k, v) -> {
                if (v == null) {
                    v = ListUtils.newArrayList();
                }
                v.add(value);
                return v;
            });
        }
    }

    /**
     * @param name name
     * @return java.util.List<java.lang.String>
     * @title getValues
     * @description
     * @author BiJi'an
     * @date 2023-03-19 11:04
     */
    public List<String> getValues(String name) {
        return configs.get(name);
    }

    /**
     * @param name name
     * @return java.lang.String
     * @title getValue
     * @description
     * @author BiJi'an
     * @date 2023-03-19 11:04
     */
    public String getValue(String name) {
        List<String> values = configs.get(name);
        if (!CollectionUtils.isEmpty(values)) {
            return values.get(0);
        }
        return StringUtils.EMPTY;
    }

    /**
     * @return java.lang.String
     * @title getConfigFile
     * @description
     * @author BiJi'an
     * @date 2023-03-19 14:33
     */
    public String getConfigFile() {
        return StringUtils.defaultString(this.getValue(CONFIG_FILE));
    }
}
