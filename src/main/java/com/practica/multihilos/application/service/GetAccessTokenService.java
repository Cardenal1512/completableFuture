package com.practica.multihilos.application.service;

import com.practica.multihilos.domain.model.AccessToken;
import com.practica.multihilos.domain.port.primary.GetAccessTokenUseCase;
import com.practica.multihilos.domain.port.secondary.TokenStoragePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAccessTokenService implements GetAccessTokenUseCase {
  private final TokenStoragePort tokenStoragePort;

  @Override
  public AccessToken get() {
    return tokenStoragePort.load();
  }
}
