package com.example.ex_bbs.controller;

import com.example.ex_bbs.Domain.Article;
import com.example.ex_bbs.Domain.Comment;
import com.example.ex_bbs.exception.ArticleNotFoundException;
import com.example.ex_bbs.form.ArticleForm;
import com.example.ex_bbs.form.CommentForm;
import com.example.ex_bbs.service.ArticleService;
import com.example.ex_bbs.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    CommentService commentService;

    @GetMapping("")
    public String article(Model model, ArticleForm articleForm, CommentForm commentForm) {
        List<Article> articleList = articleService.showAll();
        model.addAttribute("articleList", articleList);
        return "ex-bbs";
    }

    @PostMapping("addArticle")
    public String addArticle(Model model, @Valid ArticleForm articleForm, BindingResult result,CommentForm commentForm) {
        if (result.hasErrors()) {
            List<Article> articleList = articleService.showAll();
            model.addAttribute("articleList", articleList);
            Article article =new Article();
            BeanUtils.copyProperties(articleForm, article);
            System.out.println(article);
            model.addAttribute("returnArticle",article);
            return "ex-bbs";
        }

        Article article = new Article();
        BeanUtils.copyProperties(articleForm, article);
        articleService.insert(article);
        return "redirect:/article";
    }

    @PostMapping("addComment")
    public String addComment(Model model, @Valid CommentForm form, BindingResult result,ArticleForm articleForm) {
        if (result.hasErrors()) {
            model.addAttribute("articleList", articleService.showAll());
            model.addAttribute("returnArticleId", Integer.parseInt(form.getArticleId()));
            return "ex-bbs";
        }
        try{
            Article article = articleService.findById(Integer.parseInt(form.getArticleId()));
            Comment comment = new Comment();
            BeanUtils.copyProperties(form, comment);
            comment.setArticleId(Integer.parseInt(form.getArticleId()));
            commentService.insert(comment);
            System.out.println(form);
        }catch (ArticleNotFoundException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "ex-bbs-error";
        }
        return "redirect:/article";
    }

    @PostMapping("deleteArticle")
    public String deleteArticle(String articleId, Model model) {
        try {
            articleService.findById(Integer.parseInt(articleId));
            articleService.deleteById(Integer.parseInt(articleId));
        } catch (ArticleNotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "ex-bbs-error";
        }

        return "redirect:/article";
    }

}
