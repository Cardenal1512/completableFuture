package com.practica.multihilos.domain.port.secondary;

import com.practica.multihilos.domain.model.Anime;

import java.util.List;

public interface AnimeSearchPort {
    List<Anime> searchAnime(String query, int limit);
}
