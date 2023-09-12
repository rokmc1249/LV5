package com.nakta.springlv1.domain.commentlike.entity;

import com.nakta.springlv1.domain.comment.entity.Comment;
import com.nakta.springlv1.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = "user_comment_like")
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public CommentLike(Comment comment, User user) {
        this.comment = comment;
        this.user = user;

    }
}
