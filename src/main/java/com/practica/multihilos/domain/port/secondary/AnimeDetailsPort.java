package com.practica.multihilos.domain.port.secondary;


import com.practica.multihilos.domain.model.AnimeDetail;

public interface AnimeDetailsPort {
    AnimeDetail getAnimeDetails(Integer animeId);
}
