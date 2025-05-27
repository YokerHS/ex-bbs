package com.example.ex_bbs.Domain;

import lombok.Data;
import java.util.List;

/**
 * 記事を表すドメインモデルクラス.
 *
 * 記事のID、タイトル、内容、そして関連するコメントのリストを保持する。
 */
@Data
public class Article {
    /**
     * 記事の一意識別子.
     */
    private Integer id;

    /**
     * 記事のタイトルまたは名前.
     */
    private String name;

    /**
     * 記事の本文内容.
     */
    private String content;

    /**
     * 記事に関連付けられたコメントのリスト.
     */
    private List<Comment> commentList;
}
