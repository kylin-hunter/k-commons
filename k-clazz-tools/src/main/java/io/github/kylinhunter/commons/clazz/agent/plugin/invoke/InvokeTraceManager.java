package io.github.kylinhunter.commons.clazz.agent.plugin.invoke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 22:29
 **/

@Data
public class InvokeTraceManager {

    private static InvokeTraceManager singleton = new InvokeTraceManager();
    private Map<Long, List<InvokeTrace>> traces = new HashMap<>();

    public static InvokeTraceManager getInstance() {
        return singleton;
    }

    public void addTrace(InvokeTrace invokeTrace) {
        this.traces.compute(invokeTrace.getThreadId(), (k, v) -> {
            if (v == null) {
                v = new ArrayList<>();
            }
            v.add(invokeTrace);
            return v;
        });
    }
}
