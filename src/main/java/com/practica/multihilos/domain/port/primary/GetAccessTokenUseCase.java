package com.practica.multihilos.domain.port.primary;

import com.practica.multihilos.domain.model.AccessToken;

public interface GetAccessTokenUseCase {
  AccessToken get();
}
