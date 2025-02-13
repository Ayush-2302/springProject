package com.practice.practice;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PracticeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PracticeApplication.class, args);
        System.out.println("server Started !!");
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
