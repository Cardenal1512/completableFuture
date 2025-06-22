package com.practica.multihilos.domain.port.primary;

public interface ExchangeAuthorizationCodeUseCase {
  String exchange(String code);
}
