package com.practica.multihilos.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class AnimeDetail {
  private Integer id;
  private String title;
  private String imageUrl;
  private String synopsis;
  private Double mean;
  private List<String> genres;
}
