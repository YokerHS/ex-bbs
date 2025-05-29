package com.example.ex_bbs.service;

import com.example.ex_bbs.Domain.Article;
import com.example.ex_bbs.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 記事（Article）に関するビジネスロジックを提供するサービスクラス.
 * <p>
 * 主な役割:
 * <ul>
 *     <li>記事一覧の取得（コメント付き）</li>
 *     <li>記事の登録</li>
 *     <li>記事および関連コメントの削除</li>
 * </ul>
 */
@Service
@Transactional
public class ArticleService {

    /** 記事に関するDB操作を行うリポジトリ */
    @Autowired
    ArticleRepository articleRepository;

    /** コメントに関するビジネスロジックを提供するサービス */
    @Autowired
    CommentService commentService;

//    public Article findById(Integer id){
//        return commentService.findById(id);
//    }

    /**
     * 全記事を取得し、それぞれの記事に対応するコメントをセットする.
     *
     * @return コメント付きの記事リスト
     */
    public List<Article> showAll() {
        return articleRepository.findAll();
    }

    /**
     * 新しい記事を登録する.
     *
     * @param article 登録する記事オブジェクト
     */
    public Article insert(Article article) {

        return  articleRepository.insert(article);
    }

    /**
     * 指定された記事IDの記事を削除し、
     * 関連するコメントもすべて削除する.
     *
     * @param id 削除する記事のID
     */
    public void deleteById(Integer id) {
        articleRepository.deleteById(id);

    }

    public Article findById(Integer id) {
        return articleRepository.findById(id);
    }
}
