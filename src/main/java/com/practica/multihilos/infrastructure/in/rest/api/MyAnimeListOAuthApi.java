package com.practica.multihilos.infrastructure.in.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "OAuth - MyAnimeList")
public interface MyAnimeListOAuthApi {

    @Operation(summary = "Intercambia el código de autorización por un token")
    @GetMapping("/api/v1/oauth/mal/callback")
    ResponseEntity<String> callback(@RequestParam String code);
}

