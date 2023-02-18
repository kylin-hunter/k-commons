package io.github.kylinhunter.commons.generator.context.bean;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 00:57
 **/
@Data
public class ModuleInfos {
    private Map<String, ModuleInfo> modules = Maps.newHashMap();

    /**
     * @param module module
     * @return void
     * @title addModule
     * @description
     * @author BiJi'an
     * @date 2023-02-18 21:34
     */
    public void add(ModuleInfo module) {
        modules.put(module.getName(), module);
    }

    /**
     * @param name name
     * @return io.github.kylinhunter.commons.generator.context.bean.ModuleInfo
     * @title get
     * @description
     * @author BiJi'an
     * @date 2023-02-18 21:35
     */
    public ModuleInfo get(String name) {
        return modules.get(name);
    }

    /**
     * @return java.util.Collection<io.github.kylinhunter.commons.generator.context.bean.ModuleInfo>
     * @title getAll
     * @description
     * @author BiJi'an
     * @date 2023-02-18 22:11
     */
    public Collection<ModuleInfo> getAll() {
        return modules.values();
    }
}
