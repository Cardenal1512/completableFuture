package com.practica.multihilos.application.service;

import com.practica.multihilos.domain.port.primary.SearchAnimeUseCase;
import com.practica.multihilos.domain.port.secondary.AnimeSearchPort;
import com.practica.multihilos.domain.model.Anime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchAnimeService implements SearchAnimeUseCase {
  private final AnimeSearchPort animeSearchPort;

  @Override
  public List<Anime> search(String query, int limit) {
    return animeSearchPort.searchAnime(query, limit);
  }
}
