package com.practica.multihilos.domain.port.primary;

import com.practica.multihilos.domain.model.AnimeDetail;

import java.util.List;

public interface SearchDetailedAnimeUseCase {
    List<AnimeDetail> searchDetailed(List<String> titles);
}
