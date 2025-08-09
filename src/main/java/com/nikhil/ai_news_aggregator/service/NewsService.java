package com.nikhil.ai_news_aggregator.service;

import com.nikhil.ai_news_aggregator.model.Article;
import com.nikhil.ai_news_aggregator.model.GNewsResponse;
import com.nikhil.ai_news_aggregator.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {

    private final RestTemplate restTemplate;
    private final ArticleRepository articleRepository; // 1. Add the repository

    @Value("${news.api.url}")
    private String apiUrl;

    @Value("${news.api.key}")
    private String apiKey;

    // 2. Update constructor to accept the repository
    public NewsService(RestTemplate restTemplate, ArticleRepository articleRepository) {
        this.restTemplate = restTemplate;
        this.articleRepository = articleRepository;
    }

    @Transactional // 3. Ensure this whole method runs in one database transaction
    public List<Article> fetchAndSaveNews() {
        String url = apiUrl + apiKey;
        GNewsResponse response = restTemplate.getForObject(url, GNewsResponse.class);

        if (response != null && response.getArticles() != null) {
            List<Article> fetchedArticles = response.getArticles();

            // 4. Use a stream to find only the articles that are NOT in the database
            List<Article> newArticles = fetchedArticles.stream()
                    .filter(article -> !articleRepository.existsByUrl(article.getUrl()))
                    .collect(Collectors.toList());

            // 5. Save the new articles in a single batch operation
            if (!newArticles.isEmpty()) {
                articleRepository.saveAll(newArticles);
            }
        } else {
            return Collections.emptyList();
        }

        // 6. Return all articles now in the database
        return articleRepository.findAll();
    }
}