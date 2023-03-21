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
 * @date 2023-03-21 23:46
 **/
public class AgentConfig {
    Map<String, List<String>> configs = new HashMap<>();

    public void add(String name, String value) {
        if (!StringUtils.isEmpty(name) && !StringUtils.isEmpty(value)) {
            configs.compute(name, (k, v) -> {
                if (v == null) {
                    v = ListUtils.newArrayList();
                }
                v.add(value);
                return v;
            });
        }
    }

    public List<String> getValues(String name) {
        return configs.get(name);
    }

    public String getValue(String name) {
        List<String> values = configs.get(name);
        if (!CollectionUtils.isEmpty(values)) {
            return values.get(0);
        }
        return StringUtils.EMPTY;
    }
}
