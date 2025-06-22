package com.practica.multihilos.domain.port.secondary;

import com.practica.multihilos.infrastructure.out.client.myanimelist.dto.AnimeDto;

import java.util.List;

public interface AnimeSearchPort {
    List<AnimeDto> searchAnime(String query, int limit);
}
