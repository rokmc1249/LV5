package com.nakta.springlv1.domain.comment.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
    private Long boardId;
    private String contents;
}
