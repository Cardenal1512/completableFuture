package com.practica.multihilos.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mal")
public class MalProperties {
    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private String codeVerifier;
    private String urlToken;
}

