package com.example.ex_bbs.repository;

import com.example.ex_bbs.Domain.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 記事（Article）に関するDB操作を行うリポジトリクラス.
 *
 */
@Repository
public class ArticleRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    @Autowired
    private CommentRepository commentRepository;

    /**
     * Articleエンティティの行マッパー.
     *
     */
    private static final RowMapper<Article> ARTICLE_ROW_MAPPER =
            new BeanPropertyRowMapper<>(Article.class);

    /**
     * 記事テーブルの全件を取得し、各記事に対応するコメントリストを設定して返す.
     *
     * @return コメント付きの記事リスト
     */
    public List<Article> findAll() {
        String sql = """
                SELECT
                    id,
                    name,
                    content
                FROM
                    articles
                """;

        List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);

        articleList.forEach(
                article -> article.setCommentList(
                        commentRepository.findByArticleId(article.getId())
                )
        );

        return articleList;
    }
}
