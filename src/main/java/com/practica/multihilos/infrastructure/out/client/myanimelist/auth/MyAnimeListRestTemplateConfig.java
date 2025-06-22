package com.practica.multihilos.infrastructure.out.client.myanimelist.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MyAnimeListRestTemplateConfig {
    private final MyAnimeListAuthInterceptor myAnimeListAuthInterceptor;

    @Bean("myAnimeListRestTemplate")
    public RestTemplate myAnimeListRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(List.of(myAnimeListAuthInterceptor));
        return restTemplate;
    }
}

