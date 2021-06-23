package com.epam.esm.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Service config.
 */
@Configuration
public class ServiceConfig {

    /**
     * Model mapper model mapper.
     *
     * @return the model mapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
