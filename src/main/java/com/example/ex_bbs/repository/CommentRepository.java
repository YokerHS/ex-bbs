package com.example.ex_bbs.repository;

import com.example.ex_bbs.Domain.Article;
import com.example.ex_bbs.Domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * コメント（Comment）に関するDB操作を行うリポジトリクラス.
 */
@Repository
public class CommentRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * Commentエンティティの行マッパー.
     *
     */
    private static final RowMapper<Comment> COMMENT_ROW_MAPPER =
            new BeanPropertyRowMapper<>(Comment.class);

    /**
     * コメントテーブルの全件を取得する.
     *
     * @return 全コメントのリスト
     */
    public List<Comment> findAll() {
        String sql = """
                SELECT
                    id,
                    name,
                    content,
                    article_id
                FROM
                    comments
                """;
        return template.query(sql, COMMENT_ROW_MAPPER);
    }

    /**
     * 指定された記事IDに紐づくコメントを取得する.
     *
     * @param articleId 紐づけたい記事のID
     * @return 該当するコメントのリスト
     */
    public List<Comment> findByArticleId(Integer articleId) {
        String sql = """
                SELECT
                    id,
                    name,
                    content,
                    article_id
                FROM
                    comments
                WHERE
                    article_id = :articleId
                """;

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("articleId", articleId);

        return template.query(sql, param, COMMENT_ROW_MAPPER);
    }
}
