package com.example.project;

import com.example.project.utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class Config {
    @Bean
        public ModelMapper modelMapper() {return new ModelMapper();}

    @Bean
    public ListMapper listMapper() {return ListMapper.getInstance();}
}
