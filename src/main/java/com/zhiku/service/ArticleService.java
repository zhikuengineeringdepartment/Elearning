package com.zhiku.service;

import com.zhiku.entity.Article;
import com.zhiku.mapper.ArticleMapper;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 获取所有课程，返回list
     */
    public List<Article> getAllArticle(){
        List<Article> articles = articleMapper.getAllArticle();
        return articles;
    }
}
