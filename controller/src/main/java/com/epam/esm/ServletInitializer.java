package com.epam.esm;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
        applicationBuilder.profiles("prod", "jpaData");
        return applicationBuilder.sources(ApplicationConfiguration.class);
    }

}
