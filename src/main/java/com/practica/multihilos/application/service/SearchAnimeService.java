package com.practica.multihilos.application.service;

import com.practica.multihilos.domain.model.AnimeDetail;
import com.practica.multihilos.domain.port.primary.SearchAnimeUseCase;
import com.practica.multihilos.domain.port.primary.SearchDetailedAnimeUseCase;
import com.practica.multihilos.domain.port.secondary.AnimeDetailsPort;
import com.practica.multihilos.domain.port.secondary.AnimeSearchPort;
import com.practica.multihilos.domain.model.Anime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class SearchAnimeService implements SearchAnimeUseCase, SearchDetailedAnimeUseCase {
  private final AnimeSearchPort animeSearchPort;
  private final AnimeDetailsPort animeDetailsPort;
  private final Executor taskExecutor;
  private static final int DEFAULT_LIMIT = 50;

  @Override
  public List<Anime> search(String query, int limit) {
    return animeSearchPort.searchAnime(query, limit);
  }

  @Override
  public List<Anime> searchMultiple(List<String> titles) {
    long start = System.currentTimeMillis();

    List<CompletableFuture<List<Anime>>> futures =
        titles.stream()
            .map(
                title ->
                    CompletableFuture.supplyAsync(
                            () -> {
                              long t0 = System.currentTimeMillis();
                              System.out.println(
                                  "🧵 Iniciando búsqueda de \""
                                      + title
                                      + "\" en hilo: "
                                      + Thread.currentThread().getName());

                              List<Anime> result =
                                  animeSearchPort.searchAnime(title, DEFAULT_LIMIT);

                              long t1 = System.currentTimeMillis();
                              System.out.println("📊 \"" + title + "\" tardó " + (t1 - t0) + " ms");

                              return result;
                            },
                            taskExecutor)
                        .orTimeout(5, TimeUnit.SECONDS)
                        .exceptionally(
                            ex -> {
                              System.err.println(
                                  "❌ Fallo o timeout buscando \""
                                      + title
                                      + "\": "
                                      + ex.getClass().getSimpleName());
                              return List.of();
                            }))
            .toList();

    List<Anime> result =
        futures.stream().map(CompletableFuture::join).flatMap(List::stream).toList();

    long end = System.currentTimeMillis();
    System.out.println("⏱️ Tiempo total de ejecución: " + (end - start) + "ms");

    return result;
  }

  @Override
  public List<AnimeDetail> searchDetailed(List<String> titles) {
      long start = System.currentTimeMillis();

      // Step 1: Get multiple search results
      List<Anime> basicResults = this.searchMultiple(titles);

      // Step 2: Limit results to prevent abuse (e.g. max 20)
      List<Anime> limitedResults = basicResults.stream().limit(20).toList();

      // Step 3: Fetch details with small delays (rate limit)
      List<CompletableFuture<AnimeDetail>> detailFutures =
              limitedResults.stream()
                      .map(anime -> CompletableFuture.supplyAsync(() -> {
                                  try {
                                      System.out.println("🔍 Fetching details for ID: " + anime.getId());
                                      // Sleep to simulate delay between calls (rate limiting)
                                      Thread.sleep(200);
                                      return animeDetailsPort.getAnimeDetails(anime.getId());
                                  } catch (Exception e) {
                                      System.err.println("❌ Failed to fetch details for ID " + anime.getId() + ": " + e.getClass().getSimpleName());
                                      return null;
                                  }
                              }, taskExecutor)
                              .orTimeout(3, TimeUnit.SECONDS)
                              .exceptionally(ex -> {
                                  System.err.println("❌ Timeout for ID " + anime.getId() + ": " + ex.getClass().getSimpleName());
                                  return null;
                              }))
                      .toList();

      List<AnimeDetail> details = detailFutures.stream()
              .map(CompletableFuture::join)
              .filter(Objects::nonNull)
              .toList();

      long end = System.currentTimeMillis();
      System.out.println("✅ Total time for search + detail: " + (end - start) + "ms and found " + details.size() + " animes");

      return details;
  }
}
