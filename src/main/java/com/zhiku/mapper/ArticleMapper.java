package com.zhiku.mapper;

import com.zhiku.entity.Article;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleMapper {
    List<Article> getAllArticle();
}
