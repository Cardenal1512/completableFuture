package com.practica.multihilos.infrastructure.in.rest.api;

import com.practica.multihilos.infrastructure.out.client.myanimelist.dto.AnimeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Anime", description = "Operaciones relacionadas con la búsqueda de anime")
public interface AnimeSearchControllerApi {

    @Operation(
            summary = "Buscar anime por nombre",
            description = "Realiza una búsqueda de animes en MyAnimeList en base a un término de consulta"
    )
    @GetMapping("/api/anime/search")
    ResponseEntity<List<AnimeDto>> searchAnime(
            @Parameter(description = "Texto de búsqueda", example = "one piece")
            @RequestParam String query,

            @Parameter(description = "Número máximo de resultados", example = "5")
            @RequestParam(defaultValue = "5") int limit
    );
}

