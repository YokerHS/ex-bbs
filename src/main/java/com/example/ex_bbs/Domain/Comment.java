package com.example.ex_bbs.Domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * コメントを表すドメインモデルクラス.
 *
 * コメントのID、投稿者の名前、コメント内容、および対象記事IDを保持する。
 */
@Data
public class Comment {
    /**
     * コメントの一意識別子.
     */
    private Integer id;

    /**
     * コメント投稿者の名前.
     */
    private String name;

    /**
     * コメント内容.
     */
    private String content;

    /**
     * このコメントが紐づく記事のID.
     */
    private Integer articleId;
}
