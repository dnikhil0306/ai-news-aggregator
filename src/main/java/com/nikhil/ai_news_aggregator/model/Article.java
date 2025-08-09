package com.nikhil.ai_news_aggregator.model;

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
@Table(name = "articles") // Explicitly name the table
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // This is our own database ID, not from the API

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    @Column(length = 1000) // Set a max length for the description
    private String description;

    @JsonProperty("content")
    @Column(length = 4000) // Set a max length for the content
    private String content;

    @JsonProperty("url")
    @Column(unique = true) // Ensure we don't save the same article twice
    private String url;

    @JsonProperty("image")
    private String image;

    @JsonProperty("publishedAt")
    private String publishedAt;

    @JsonProperty("source")
    @Transient // Tells the database to ignore this field
    private Source source;
}