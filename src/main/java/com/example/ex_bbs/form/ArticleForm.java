package com.example.ex_bbs.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 記事投稿フォームのデータを格納するフォームクラス.
 *
 */
@Data
public class ArticleForm {

    /** 投稿者の名前 */
    @NotBlank(message = "投稿者名を入力してください")
    @Size(max = 50, message = "投稿者名は50文字以内で入力してください")
    private String name;

    /** 記事の内容 */
    @NotBlank(message = "投稿内容を入力してください")
    private String content;
}
