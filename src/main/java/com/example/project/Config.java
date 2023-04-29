package com.example.project;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class Config {
    @Bean
        public ModelMapper modelMapper() {return new ModelMapper();}
}
