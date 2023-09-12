package com.nakta.springlv1.domain.comment.service;

import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.board.repository.BoardRepository;
import com.nakta.springlv1.domain.comment.dto.CommentRequestDto;
import com.nakta.springlv1.domain.comment.dto.CommentResponseDto;
import com.nakta.springlv1.domain.comment.entity.Comment;
import com.nakta.springlv1.domain.comment.repository.CommentRepository;
import com.nakta.springlv1.domain.commentlike.entity.CommentLike;
import com.nakta.springlv1.domain.commentlike.repository.CommentLikeRepository;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.jwt.JwtUtil;
import com.nakta.springlv1.domain.user.jwt.UserRoleEnum;
import com.nakta.springlv1.global.exception.CustomException;
import com.nakta.springlv1.global.exception.ErrorCode;
import com.nakta.springlv1.domain.user.entity.User;
import com.nakta.springlv1.domain.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public CommentResponseDto createComment(CommentRequestDto requestDto, User user) {

        Board board = findBoard(requestDto.getBoardId());// DB에 해당 username 있는지 확인
        Comment comment = new Comment(requestDto, board, user);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto modifyComment(Long id, CommentRequestDto requestDto, User user) {

        //DB저장 유무
        Comment comment = findComment(id);
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUsername().equals(user.getUsername())) {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public StringResponseDto deleteComment(Long id, User user) {

        //DB저장 유무
        Comment comment = findComment(id);
        if (!user.getRole().equals(UserRoleEnum.ADMIN) && !comment.getUsername().equals(user.getUsername())) {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
        commentRepository.delete(comment);
        return new StringResponseDto("삭제를 성공하였음");

    }
    @Transactional
    public StringResponseDto likeComment(Long id, User user){
        Comment comment = findComment(id);
        CommentLike overlap = commentLikeRepository.findByCommentAndUser(comment,user);

        if(overlap!=null){
            commentLikeRepository.delete(overlap); // 좋아요 삭제
            comment.unlike(); // 해당 댓글 좋아요 취소시키는 메서드
            return new StringResponseDto("좋아요 취소");
        }
        else{
            CommentLike commentLike = new CommentLike(comment,user);
            commentLikeRepository.save(commentLike); // 좋아요를 누른 user/comment id 저장
            comment.like(); // 해당 댓글 좋아요수 증가시키는 메서드
            return new StringResponseDto("좋아요 확인");
        }
    }

    private Comment findComment(Long id2) {
        return commentRepository.findById(id2).orElseThrow(() ->
                new CustomException(ErrorCode.COMMENT_NOT_FOUND)
        );
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new CustomException(ErrorCode.POST_NOT_FOUND)
        );
    }
}
