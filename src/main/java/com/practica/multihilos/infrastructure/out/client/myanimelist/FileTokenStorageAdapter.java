package com.practica.multihilos.infrastructure.out.client.myanimelist;

import com.practica.multihilos.domain.model.AccessToken;
import com.practica.multihilos.domain.port.secondary.TokenStoragePort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileTokenStorageAdapter implements TokenStoragePort {

    private static final Path TOKEN_PATH = Paths.get("tokens/access-token.txt");

    @Override
    public AccessToken load() {
        try {
            String token = Files.readString(TOKEN_PATH);
            AccessToken accessToken = new AccessToken(token);
            return accessToken;
        } catch (IOException e) {
            throw new RuntimeException("No se pudo leer el token", e);
        }
    }

    @Override
    public void save(AccessToken token) {
        try {
            Files.createDirectories(TOKEN_PATH.getParent());
            Files.writeString(TOKEN_PATH, token.value());
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar el token", e);
        }
    }
}

