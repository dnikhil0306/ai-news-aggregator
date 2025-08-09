package com.nikhil.ai_news_aggregator.repository;

import com.nikhil.ai_news_aggregator.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marks this as a Spring repository bean
public interface ArticleRepository extends JpaRepository<Article, Long> {

    // Spring Data JPA will automatically create a query for this method:
    // "SELECT COUNT(*) > 0 FROM articles WHERE url = ?"
    boolean existsByUrl(String url);
}