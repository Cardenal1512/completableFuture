package com.practica.multihilos.infrastructure.out.client.myanimelist.dto;

import lombok.Data;
import java.util.List;

@Data
public class AnimeDetailResponse {
    private Integer id;
    private String title;
    private MainPicture main_picture;
    private String synopsis;
    private Double mean;
    private List<Genre> genres;

    @Data
    public static class MainPicture {
        private String medium;
    }

    @Data
    public static class Genre {
        private String name;
    }
}
