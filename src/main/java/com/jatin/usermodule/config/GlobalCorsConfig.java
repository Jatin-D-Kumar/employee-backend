package com.jatin.usermodule.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class GlobalCorsConfig implements WebMvcConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(GlobalCorsConfig.class);

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        logger.info("CORS configuration applied");
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // frontend url
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
