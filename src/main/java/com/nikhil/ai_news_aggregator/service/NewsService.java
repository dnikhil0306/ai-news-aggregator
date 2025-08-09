package com.nikhil.ai_news_aggregator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nikhil.ai_news_aggregator.model.Article;
import com.nikhil.ai_news_aggregator.model.GNewsResponse;
import com.nikhil.ai_news_aggregator.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final RestTemplate restTemplate;
    private final ArticleRepository articleRepository;
    private final ObjectMapper objectMapper; // For manual JSON parsing

    @Value("${news.api.url}")
    private String apiUrl;

    @Value("${news.api.key}")
    private String apiKey;

    // Updated constructor to accept ObjectMapper
    public NewsService(RestTemplate restTemplate, ArticleRepository articleRepository, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.articleRepository = articleRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public List<Article> fetchAndSaveNews() {
        String url = apiUrl + apiKey;

        // STEP 1: Get the response as raw text (String)
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        String jsonResponse = responseEntity.getBody();

        // STEP 2: PRINT THE RAW RESPONSE TO THE CONSOLE
        System.out.println("==================== RAW GNEWS API RESPONSE ====================");
        System.out.println(jsonResponse);
        System.out.println("================================================================");

        GNewsResponse response;
        try {
            // STEP 3: Manually parse the JSON text
            response = objectMapper.readValue(jsonResponse, GNewsResponse.class);
        } catch (JsonProcessingException e) {
            // If parsing fails, print the error and stop.
            e.printStackTrace();
            throw new RuntimeException("Failed to parse GNews response", e);
        }

        if (response != null && response.getArticles() != null) {
            List<Article> fetchedArticles = response.getArticles();

            List<Article> newArticles = fetchedArticles.stream()
                    .filter(article -> !articleRepository.existsByUrl(article.getUrl()))
                    .collect(Collectors.toList());

            if (!newArticles.isEmpty()) {
                articleRepository.saveAll(newArticles);
            }
        } else {
            return Collections.emptyList();
        }

        return articleRepository.findAll();
    }
}