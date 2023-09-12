package com.nakta.springlv1.domain.comment.dto;

import com.nakta.springlv1.domain.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class CommentResponseDto {
    private Long id;
    private Long boardId;
    private String contents;
    private LocalDateTime modifiedAt; // 게시글 수정 날짜
    private LocalDateTime createAt; // 게시글 생성 날짜
    private int commentLike;
    private String username;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();
        this.modifiedAt = comment.getModifiedAt();
        this.createAt = comment.getCreatedAt();
        this.username = comment.getUsername();
        this.commentLike = comment.getCommentLike();
        this.boardId = comment.getBoard().getId();
    }
}

