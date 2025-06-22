package com.practica.multihilos.domain.port.secondary;

import com.practica.multihilos.domain.model.AccessToken;

public interface TokenStoragePort {
  AccessToken load();

  void save(AccessToken token);
}
