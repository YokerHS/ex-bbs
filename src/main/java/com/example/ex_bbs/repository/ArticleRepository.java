package com.example.ex_bbs.repository;

import com.example.ex_bbs.Domain.Article;
import com.example.ex_bbs.Domain.Comment;
import com.example.ex_bbs.exception.ArticleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 記事（Article）に関するDB操作を行うリポジトリクラス。
 * <p>
 * 主な機能:
 * <ul>
 *     <li>記事の取得（全件）</li>
 *     <li>記事の挿入</li>
 *     <li>記事の削除</li>
 * </ul>
 */
@Repository
public class ArticleRepository {

    /** NamedParameterJdbcTemplateのインスタンス。SQLの実行に使用. */
    @Autowired
    private NamedParameterJdbcTemplate template;


    public Article findById(Integer id) {
        String sql = """
            SELECT
                id,
                name,
                content
            FROM
                articles
            WHERE
                id = :id
            """;

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            return template.queryForObject(sql, param, ARTICLE_ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            throw new ArticleNotFoundException("不正な操作が行われました (id: " + id + ")");
        }
    }



    /**
     * Articleエンティティ用のRowMapper.
     * <p>
     * DBから取得したデータをArticleオブジェクトにマッピングする。
     */
    private static final RowMapper<Article> ARTICLE_ROW_MAPPER =
            new BeanPropertyRowMapper<>(Article.class);

    /**
     * 記事テーブルから全記事を取得する.
     * <p>
     * 記事はIDの降順（新しい順）で並べ替えて返す。
     *
     * @return 記事のリスト
     */
    public List<Article> findAll() {
        String sql = """
            SELECT
                a.id AS id,
                a.name AS name,
                a.content AS content,
                c.id AS com_id,
                c.name AS com_name,
                c.content AS com_content,
                c.article_id AS article_id
            FROM
                articles AS a
            LEFT OUTER JOIN
                comments AS c
            ON
                a.id = c.article_id
            ORDER BY
                a.id DESC,
                c.id DESC
            """;

        return template.query(sql, rs -> {
            Map<Integer, Article> articleMap = new LinkedHashMap<>();

            while (rs.next()) {
                Integer articleId = rs.getInt("id");
                Article article = articleMap.get(articleId);
                if (article == null) {
                    article = new Article();
                    article.setId(articleId);
                    article.setName(rs.getString("name"));
                    article.setContent(rs.getString("content"));
                    article.setCommentList(new ArrayList<>());
                    articleMap.put(articleId, article);
                }

                int commentId = rs.getInt("com_id");
                if (commentId > 0){
                    Comment comment = new Comment();
                    comment.setId(commentId);
                    comment.setName(rs.getString("com_name"));
                    comment.setContent(rs.getString("com_content"));
                    comment.setArticleId(rs.getInt("article_id"));
                    System.out.println(comment);
                    article.getCommentList().add(comment);
                }

            }
            return new ArrayList<>(articleMap.values());
        });
    }


    /**
     * 新しい記事をDBに挿入する.
     * <p>
     * 挿入後、自動採番されたIDをArticleオブジェクトに設定する。
     *
     * @param article 挿入対象の記事オブジェクト
     */
    public Article insert(Article article) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(article);

        String sql = """
            INSERT INTO
                articles
                (name, content)
            VALUES
                (:name, :content)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder, new String[] {"id"});
        article.setId(keyHolder.getKey().intValue());

        return article;
    }

    /**
     * 指定されたIDの記事をDBから削除する.
     *
     * @param id 削除対象の記事ID
     */
    public void deleteById(Integer id) {
        String sql = """
                BEGIN;
                
                DELETE FROM comments WHERE article_id = :id;
                DELETE FROM articles WHERE id = :id;
                
                COMMIT;
                """;

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        template.update(sql, param);
    }
}
