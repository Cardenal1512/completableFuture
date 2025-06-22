package com.practica.multihilos.infrastructure.out.client.myanimelist;

import com.practica.multihilos.domain.port.secondary.AnimeSearchPort;
import com.practica.multihilos.domain.model.Anime;
import com.practica.multihilos.infrastructure.out.client.myanimelist.dto.AnimeSearchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MyAnimeListApiClient implements AnimeSearchPort {
  private final RestTemplate restTemplate;

  public MyAnimeListApiClient(@Qualifier("myAnimeListRestTemplate") RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Override
  public List<Anime> searchAnime(String query, int limit) {
    String url = "https://api.myanimelist.net/v2/anime?q=" + query + "&limit=" + limit;

    try {
      ResponseEntity<AnimeSearchResponse> response =
          restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, AnimeSearchResponse.class);

      // Aquí podrías mapear la respuesta JSON a un DTO si prefieres
      return response.getBody().getData().stream()
              .map(data -> {
                Anime dto = new Anime();
                dto.setId(data.getNode().getId());
                dto.setTitle(data.getNode().getTitle());
                dto.setImageUrl(data.getNode().getMain_picture().getMedium());
                return dto;
              })
              .toList();

    } catch (Exception e) {
      throw new RuntimeException("No se pudo obtener resultados desde MyAnimeList", e);
    }
  }
}
