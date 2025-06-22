package com.practica.multihilos.domain.port.primary;

import com.practica.multihilos.domain.model.Anime;

import java.util.List;

public interface SearchAnimeUseCase {
    List<Anime> search(String query, int limit);
}
