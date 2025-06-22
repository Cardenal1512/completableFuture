package com.practica.multihilos.application.service;

import com.practica.multihilos.domain.port.primary.SearchAnimeUseCase;
import com.practica.multihilos.domain.port.secondary.AnimeSearchPort;
import com.practica.multihilos.domain.model.Anime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SearchAnimeService implements SearchAnimeUseCase {
  private final AnimeSearchPort animeSearchPort;
  private final Executor taskExecutor;
  private static final int DEFAULT_LIMIT = 30;

  @Override
  public List<Anime> search(String query, int limit) {
    return animeSearchPort.searchAnime(query, limit);
  }

  @Override
  public List<Anime> searchMultiple(List<String> titles) {
    long start = System.currentTimeMillis();

    List<CompletableFuture<List<Anime>>> futures = titles.stream()
            .map(title -> CompletableFuture
                    .supplyAsync(() -> {
                      long t0 = System.currentTimeMillis();
                      System.out.println("🧵 Iniciando búsqueda de \"" + title + "\" en hilo: " + Thread.currentThread().getName());

                      List<Anime> result = animeSearchPort.searchAnime(title, DEFAULT_LIMIT);

                      long t1 = System.currentTimeMillis();
                      System.out.println("📊 \"" + title + "\" tardó " + (t1 - t0) + " ms");

                      return result;
                    }, taskExecutor)
                    .orTimeout(1, TimeUnit.SECONDS)
                    .exceptionally(ex -> {
                      System.err.println("❌ Fallo o timeout buscando \"" + title + "\": " + ex.getClass().getSimpleName());
                      return List.of();
                    }))
            .toList();

    List<Anime> result = futures.stream()
            .map(CompletableFuture::join)
            .flatMap(List::stream)
            .toList();

    long end = System.currentTimeMillis();
    System.out.println("⏱️ Tiempo total de ejecución: " + (end - start) + "ms");

    return result;
  }

}
