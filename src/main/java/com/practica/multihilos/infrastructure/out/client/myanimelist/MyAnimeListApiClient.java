package com.practica.multihilos.infrastructure.out.client.myanimelist;

import com.practica.multihilos.domain.model.AnimeDetail;
import com.practica.multihilos.domain.port.secondary.AnimeDetailsPort;
import com.practica.multihilos.domain.port.secondary.AnimeSearchPort;
import com.practica.multihilos.domain.model.Anime;
import com.practica.multihilos.infrastructure.out.client.myanimelist.dto.AnimeDetailResponse;
import com.practica.multihilos.infrastructure.out.client.myanimelist.dto.AnimeSearchResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MyAnimeListApiClient implements AnimeSearchPort, AnimeDetailsPort {
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

    @Override
    public AnimeDetail getAnimeDetails(Integer animeId) {
        String url = "https://api.myanimelist.net/v2/anime/" + animeId +
                "?fields=id,title,main_picture,synopsis,mean,genres";

        try {
            ResponseEntity<AnimeDetailResponse> response =
                    restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, AnimeDetailResponse.class);

            AnimeDetailResponse body = response.getBody();
            if (body == null) throw new RuntimeException("Respuesta vacía");

            AnimeDetail detail = new AnimeDetail();
            detail.setId(body.getId());
            detail.setTitle(body.getTitle());
            detail.setImageUrl(body.getMain_picture() != null ? body.getMain_picture().getMedium() : null);
            detail.setSynopsis(body.getSynopsis());
            detail.setMean(body.getMean());
            detail.setGenres(
                    body.getGenres() != null
                            ? body.getGenres().stream().map(AnimeDetailResponse.Genre::getName).toList()
                            : List.of()
            );

            return detail;

        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener el detalle del anime ID " + animeId, e);
        }
    }
}
