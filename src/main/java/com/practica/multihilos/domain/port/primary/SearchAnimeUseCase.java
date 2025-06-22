package com.practica.multihilos.domain.port.primary;

import com.practica.multihilos.infrastructure.out.client.myanimelist.dto.AnimeDto;

import java.util.List;

public interface SearchAnimeUseCase {
    List<AnimeDto> search(String query, int limit);
}
