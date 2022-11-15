package com.kylinhunter.plat.commons.exception.configuration;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kylinhunter.plat.commons.exception.explain.ExplainCustomizer;
import com.kylinhunter.plat.commons.exception.explain.ExceptionExplainer;
import com.kylinhunter.plat.commons.exception.info.ErrInfoCustomizer;
import com.kylinhunter.plat.commons.exception.info.ErrInfoManager;

import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-07 23:20
 **/
@Configuration
@RequiredArgsConstructor
public class ExceptionConfiguration {

    @Bean
    public ErrInfoManager errCodeManager(Map<String, ErrInfoCustomizer> errCustomizers) {
        ErrInfoManager errInfoManager = new ErrInfoManager();

        errCustomizers.values().forEach(errInfoCustomizer -> {
            errInfoCustomizer.customize(errInfoManager);
        });

        return errInfoManager;
    }

    @Bean
    public ExceptionExplainer exceptionExplainer(Map<String, ExplainCustomizer> exceptionExplainCustomizers) {
        final ExceptionExplainer exceptionExplainer = new ExceptionExplainer();
        exceptionExplainCustomizers.values()
                .forEach(exceptionExplainCustomizer -> exceptionExplainCustomizer.customize(exceptionExplainer));
        return exceptionExplainer;

    }


}
