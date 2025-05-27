package com.example.ex_bbs.controller;

import com.example.ex_bbs.Domain.Article;
import com.example.ex_bbs.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    @GetMapping("")
    public String article(Model model){
        List<Article> articleList = articleService.showAll();
        model.addAttribute("articleList",articleList);
        return "ex-bbs";
    }
}
