package com.nikhil.ai_news_aggregator.controller;

import com.nikhil.ai_news_aggregator.model.Article;
import com.nikhil.ai_news_aggregator.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController // 1. Marks this class as a request handler
@RequestMapping("/api") // 2. Sets the base URL for all endpoints in this class
public class NewsController {

    private final NewsService newsService;

    // 3. Injects the NewsService
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping("/news") // 4. Maps GET requests for /api/news to this method
    public List<Article> getNews() {
        return newsService.fetchNews(); // 5. Calls the service and returns the data
    }
}