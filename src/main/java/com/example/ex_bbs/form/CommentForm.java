package com.example.ex_bbs.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * コメント投稿フォームのデータを格納するフォームクラス.
 *
 */
@Data
public class CommentForm {

    /** コメント対象の記事ID（文字列形式） */
    private String articleId;

    /** コメント投稿者の名前 */
    @NotBlank(message = "名前を入力してください")
    @Size(max = 50,message = "名前は50文字以内で入力してください")
    private String name;

    /** コメントの内容 */
    @NotBlank(message = "コメントを入力してください")
    private String content;
}
