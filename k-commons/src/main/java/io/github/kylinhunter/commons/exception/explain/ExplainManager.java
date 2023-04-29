package io.github.kylinhunter.commons.exception.explain;

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.collections.SetUtils;
import io.github.kylinhunter.commons.exception.ExceptionFinder;
import io.github.kylinhunter.commons.exception.common.KThrowable;
import io.github.kylinhunter.commons.exception.embed.InitException;
import io.github.kylinhunter.commons.exception.info.ErrInfos;
import io.github.kylinhunter.commons.sys.KConst;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * @author BiJi'an
 * @description
 * @date 2021/8/1
 **/
public class ExplainManager {

    private static Explainers explainers;

    static {
        init(KConst.K_BASE_PACKAGE);
    }

    /**
     * @param pkgs pkgs
     * @return void
     * @title init
     * @description
     * @author BiJi'an
     * @date 2022-11-24 01:30
     */
    public static void init(String... pkgs) {
        explainers = new Explainers();
        for (String pkg : pkgs) {
            Reflections reflections = new Reflections(pkg, Scanners.SubTypes);
            Set<Class<? extends AbstractExplainerSupplier>> classes =
                    reflections.getSubTypesOf(AbstractExplainerSupplier.class);
            for (Class<? extends AbstractExplainerSupplier> clazz : classes) {
                try {
                    explainers.add(clazz.newInstance().get());
                } catch (Exception e) {
                    throw new InitException("init  explainer error " + clazz.getName(), e);
                }
            }
        }

    }

    /**
     * @param throwable throwable
     * @return io.github.kylinhunter.commons.exception.explain.Explainer.ExplainResult
     * @title explain
     * @description
     * @author BiJi'an
     * @date 2022-11-24 12:03
     */
    public static Explainer.ExplainResult explain(Throwable throwable) {

        Explainer.ExplainResult explainResult = null;
        if (throwable instanceof KThrowable) {
            explainResult = new Explainer.ExplainResult((KThrowable) throwable, throwable.getMessage());
        } else {
            ExceptionFinder.ExceptionFind exceptionFind =
                    ExceptionFinder.find(throwable, true, explainers.allThrowables);
            if (exceptionFind != null) {
                Function<Throwable, Explainer.ExplainResult> explainer =
                        explainers.allExplainers.get(exceptionFind.getSource());
                if (explainer != null) {
                    explainResult = explainer.apply(exceptionFind.getTarget());
                }
            }
            if (explainResult == null) {
                explainResult = new Explainer.ExplainResult(ErrInfos.UNKNOWN, throwable.getMessage());
            }
        }
        return explainResult;
    }

    /**
     * @author BiJi'an
     * @description
     * @date 2022/8/1
     **/
    static class Explainers {
        private final Map<Class<? extends Throwable>, Function<Throwable, Explainer.ExplainResult>> allExplainers =
                MapUtils.newHashMap();
        public final Set<Class<? extends Throwable>> allThrowables = SetUtils.newHashSet();

        @SuppressWarnings({"unchecked", "rawtypes"})
        public void add(List<Explainer> explainers) {
            explainers.forEach(explain -> {

                allExplainers.put(explain.getSource(), explain.getExplainer());
                allThrowables.add(explain.getSource());
            });
        }
    }

}
