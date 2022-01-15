package com.debugger.quickpoll;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class QuickPollMvcConfigAdapter implements WebMvcConfigurer {
        @Override
public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
PageableHandlerMethodArgumentResolver phmar = new PageableHandlerMethodArgumentResolver();
            // Set the default size to 5. You change this value any time 
            phmar.setFallbackPageable(PageRequest.of(0, 5));
            argumentResolvers.add(phmar);
            
            // http://localhost:8080/v2/polls/?sort=question,asc&sort=id,desc
            // Return results in ascending order
        }
}