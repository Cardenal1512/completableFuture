package com.practica.multihilos.domain.port.secondary;

public interface TokenExchangePort {
    String exchangeAuthorizationCode(String code);
}
