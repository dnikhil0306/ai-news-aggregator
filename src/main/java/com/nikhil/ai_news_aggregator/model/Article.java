package com.nikhil.ai_news_aggregator.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "articles")
@JsonIgnoreProperties("id")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    @Column(length = 1000)
    private String description;

    @JsonProperty("content")
    @Column(length = 4000)
    private String content;

    @JsonProperty("url")
    @Column(unique = true, length = 1024)
    private String url;

    @JsonProperty("image")
    @Column(length = 1024)
    private String image;

    @JsonProperty("publishedAt")
    private String publishedAt;

    @JsonProperty("source")
    @Transient
    private Source source;
}