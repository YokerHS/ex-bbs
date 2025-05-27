package com.example.ex_bbs.service;

import com.example.ex_bbs.Domain.Article;
import com.example.ex_bbs.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> showAll(){
        return articleRepository.findAll();
    }
}
