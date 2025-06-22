package com.practica.multihilos.application.service;

import com.practica.multihilos.domain.port.primary.ExchangeAuthorizationCodeUseCase;
import com.practica.multihilos.domain.port.secondary.TokenExchangePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyAnimeListService implements ExchangeAuthorizationCodeUseCase {

  private final TokenExchangePort tokenExchangePort;

  @Override
  public String exchange(String code) {
    return tokenExchangePort.exchangeAuthorizationCode(code);
  }
}
