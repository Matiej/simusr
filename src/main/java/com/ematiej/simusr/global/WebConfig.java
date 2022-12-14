package com.ematiej.simusr.global;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value(value = "${cors.origin.webui}")
    private String[] uiCors;
    @Value(value = "${cors.max.age}")
    private int maxAge;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(uiCors)
                .maxAge(maxAge)
                .allowCredentials(true);
    }
}
