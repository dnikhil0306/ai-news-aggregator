package com.nikhil.ai_news_aggregator.service;

import com.nikhil.ai_news_aggregator.model.Article;
import com.nikhil.ai_news_aggregator.model.GNewsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service // 1. Marks this class as a Spring Service
public class NewsService {

    private final RestTemplate restTemplate;

    // 2. Injects values from application.properties
    @Value("${news.api.url}")
    private String apiUrl;

    @Value("${news.api.key}")
    private String apiKey;

    // 3. Constructor for dependency injection
    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Article> fetchNews() {
        // 4. Builds the full URL and makes the API call
        String url = apiUrl + apiKey;
        GNewsResponse response = restTemplate.getForObject(url, GNewsResponse.class);

        // 5. Returns the list of articles from the response
        if (response != null && response.getArticles() != null) {
            return response.getArticles();
        }
        return List.of(); // Return an empty list if something goes wrong
    }
}