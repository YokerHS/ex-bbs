package com.example.ex_bbs.repository;

import com.example.ex_bbs.Domain.Article;
import com.example.ex_bbs.Domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * コメント（Comment）に関するDB操作を行うリポジトリクラス.
 * <p>
 * 主な機能:
 * <ul>
 *     <li>全コメントの取得</li>
 *     <li>記事IDでのコメント取得</li>
 *     <li>コメントの追加</li>
 *     <li>記事IDに基づくコメントの削除</li>
 * </ul>
 */
@Repository
public class CommentRepository {

    /** SQL操作に使用するNamedParameterJdbcTemplateのインスタンス */
    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * Commentエンティティ用のRowMapper.
     * DBの行データをCommentオブジェクトへマッピングする。
     */
    private static final RowMapper<Comment> COMMENT_ROW_MAPPER =
            new BeanPropertyRowMapper<>(Comment.class);

    /**
     * コメントテーブルから全件を取得する.
     *
     * @return コメントのリスト
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
     * 指定された記事IDに紐づくコメントを全て取得する.
     * IDの降順（新しい順）で返す.
     *
     * @param articleId 対象の記事ID
     * @return 該当記事のコメントリスト
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
                ORDER BY
                    id DESC
                """;

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("articleId", articleId);

        return template.query(sql, param, COMMENT_ROW_MAPPER);
    }

    /**
     * 新しいコメントをデータベースに挿入する.
     * 挿入後に自動採番されたIDをCommentオブジェクトへ設定する.
     *
     * @param comment 挿入するコメントオブジェクト
     */
    public void insert(Comment comment) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(comment);

        String sql = """
            INSERT INTO
                comments
                (name, content, article_id)
            VALUES
                (:name, :content, :articleId)
            """;

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder, new String[] {"id"});
        comment.setId(keyHolder.getKey().intValue());
    }

    /**
     * 指定された記事IDに紐づく全てのコメントを削除する.
     *
     * @param articleId コメントを削除する対象の記事ID
     */
    public void deleteByArticleId(Integer articleId) {
        String sql = """
                DELETE FROM
                    comments
                WHERE
                    article_id = :articleId
                """;

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("articleId", articleId);

        template.update(sql, param);
    }
}
