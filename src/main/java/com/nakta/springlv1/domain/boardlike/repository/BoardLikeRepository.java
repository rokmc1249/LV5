package com.nakta.springlv1.domain.boardlike.repository;

import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.boardlike.entity.BoardLike;
import com.nakta.springlv1.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BoardLikeRepository extends JpaRepository<BoardLike,Long> {
    BoardLike findByBoardAndUser(Board board, User user);
}
