package com.nakta.springlv1.domain.boardlike.entity;

import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.comment.entity.Comment;
import com.nakta.springlv1.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "user_board_like")
public class BoardLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BoardLike(Board board, User user) {
        this.board = board;
        this.user = user;
    }
}
