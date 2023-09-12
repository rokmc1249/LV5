package com.nakta.springlv1.domain.board.dto;

import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.comment.dto.CommentResponseDto;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long id;
    private String title;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private int boardLike;
    private List<CommentResponseDto> commentList;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.content = board.getContent();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.boardLike = board.getBoardLike();
        this.commentList = board.getCommentList().stream().map(CommentResponseDto::new)
                .sorted(Comparator.comparing(CommentResponseDto::getCreateAt).reversed())
                .toList();
    }
}
