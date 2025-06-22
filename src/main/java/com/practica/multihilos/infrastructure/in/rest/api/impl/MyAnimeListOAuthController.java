package com.practica.multihilos.infrastructure.in.rest.api.impl;

import com.practica.multihilos.application.service.MyAnimeListService;
import com.practica.multihilos.infrastructure.in.rest.api.MyAnimeListOAuthApi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MyAnimeListOAuthController implements MyAnimeListOAuthApi {

    private final MyAnimeListService myAnimeListService;

    @Override
    public ResponseEntity<String> callback(String code) {
        String token = myAnimeListService.exchange(code);
        return ResponseEntity.ok("✅ Token obtenido:\n" + token);
    }
}

