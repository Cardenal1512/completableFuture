package com.practica.multihilos.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public Executor taskExecutor() {
        return Executors.newFixedThreadPool(5);
    }
}
