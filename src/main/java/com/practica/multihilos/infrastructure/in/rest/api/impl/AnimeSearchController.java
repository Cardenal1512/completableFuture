package com.practica.multihilos.infrastructure.in.rest.api.impl;

import com.practica.multihilos.domain.port.primary.SearchAnimeUseCase;
import com.practica.multihilos.infrastructure.in.rest.api.AnimeSearchControllerApi;
import com.practica.multihilos.domain.model.Anime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AnimeSearchController implements AnimeSearchControllerApi {
  private final SearchAnimeUseCase searchAnimeUseCase;

  @Override
  public ResponseEntity<List<Anime>> searchAnime(String query, int limit) {
    List<Anime> result = searchAnimeUseCase.search(query, limit);
    return ResponseEntity.ok(result);
  }

  @Override
  public ResponseEntity<List<Anime>> searchMultipleAnimes(List<String> titles) {
    return ResponseEntity.ok(searchAnimeUseCase.searchMultiple(titles));
  }
}
