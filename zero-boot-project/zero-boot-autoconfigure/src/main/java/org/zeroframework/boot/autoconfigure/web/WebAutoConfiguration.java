package org.zeroframework.boot.autoconfigure.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zeroframework.boot.web.GlobalExceptionHandler;
import org.zeroframework.boot.web.WebLogAspect;

/**
 * @author lijianqing
 */
@Slf4j
@Configuration
public class WebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public WebLogAspect webLogAspect() {
        log.info("zero-boot-starter-web 加载了 bean {}", WebLogAspect.class.getName());
        return new WebLogAspect();
    }

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        log.info("zero-boot-starter-web 加载了 bean {}", GlobalExceptionHandler.class.getName());
        return new GlobalExceptionHandler();
    }

}
