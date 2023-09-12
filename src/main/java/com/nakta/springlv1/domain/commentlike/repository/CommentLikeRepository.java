package com.nakta.springlv1.domain.commentlike.repository;

import com.nakta.springlv1.domain.boardlike.entity.BoardLike;
import com.nakta.springlv1.domain.comment.entity.Comment;
import com.nakta.springlv1.domain.commentlike.entity.CommentLike;
import com.nakta.springlv1.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLike,Long> {
    CommentLike findByCommentAndUser(Comment comment, User user);

}
