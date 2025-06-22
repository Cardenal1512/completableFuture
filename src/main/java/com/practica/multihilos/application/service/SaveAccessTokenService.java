package com.practica.multihilos.application.service;

import com.practica.multihilos.domain.model.AccessToken;
import com.practica.multihilos.domain.port.primary.SaveAccessTokenUseCase;
import com.practica.multihilos.domain.port.secondary.TokenStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SaveAccessTokenService implements SaveAccessTokenUseCase {
  private final TokenStoragePort tokenStoragePort;

  @Override
  public void save(AccessToken token) {
    tokenStoragePort.save(token);
  }
}
