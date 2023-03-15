package io.github.kylinhunter.commons.clazz.agent.plugin.mca;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-15 17:29
 **/
public class InvokeTraceManager {

    Map<String, List<InvokeTrace>> traces = Maps.newConcurrentMap();

    public void addTrace(InvokeTrace invokeTrace){
        this.traces.put()
    }
}
