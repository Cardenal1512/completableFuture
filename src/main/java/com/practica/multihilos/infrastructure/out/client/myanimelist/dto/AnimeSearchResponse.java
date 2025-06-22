package com.practica.multihilos.infrastructure.out.client.myanimelist.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnimeSearchResponse {
    private List<AnimeData> data;

    @Data
    public static class AnimeData {
        private AnimeNode node;
    }

    @Data
    public static class AnimeNode {
        private Integer id;
        private String title;
        private MainPicture main_picture;
    }

    @Data
    public static class MainPicture {
        private String medium;
        private String large;
    }
}

