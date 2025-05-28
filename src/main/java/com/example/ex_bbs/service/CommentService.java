package com.example.ex_bbs.service;

import com.example.ex_bbs.Domain.Comment;
import com.example.ex_bbs.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * コメント（Comment）に関するビジネスロジックを提供するサービスクラス.
 * <p>
 * 主な機能:
 * <ul>
 *     <li>記事IDによるコメントの取得</li>
 *     <li>コメントの追加</li>
 *     <li>記事IDに紐づくコメントの削除</li>
 * </ul>
 */
@Service
public class CommentService {

    /** コメントに関するDB操作を行うリポジトリ */
    @Autowired
    private CommentRepository commentRepository;

    /**
     * 指定された記事IDに紐づくコメントを全て取得する.
     *
     * @param id 対象の記事ID
     * @return コメントのリスト
     */
    public List<Comment> findByArticleById(Integer id){
        return commentRepository.findByArticleId(id);
    }

    /**
     * 新しいコメントを登録する.
     *
     * @param comment 登録するコメントオブジェクト
     */
    public void insert(Comment comment) {
        commentRepository.insert(comment);
    }

    /**
     * 指定された記事IDに紐づく全てのコメントを削除する.
     *
     * @param id 対象の記事ID
     */
    public void deleteByArticleId(Integer id) {
        commentRepository.deleteByArticleId(id);
    }
}
