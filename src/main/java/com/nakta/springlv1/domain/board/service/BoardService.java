package com.nakta.springlv1.domain.board.service;

import com.nakta.springlv1.domain.board.dto.BoardRequestDto;
import com.nakta.springlv1.domain.board.dto.BoardResponseDto;
import com.nakta.springlv1.domain.board.entity.Board;
import com.nakta.springlv1.domain.board.repository.BoardRepository;
import com.nakta.springlv1.domain.boardlike.entity.BoardLike;
import com.nakta.springlv1.domain.boardlike.repository.BoardLikeRepository;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.entity.User;
import com.nakta.springlv1.domain.user.jwt.JwtUtil;
import com.nakta.springlv1.domain.user.jwt.UserRoleEnum;
import com.nakta.springlv1.global.exception.CustomException;
import com.nakta.springlv1.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {

        Board board = new Board(requestDto, user); //username을 따로 받기 위한 생성자 생성
        Board newboard = boardRepository.save(board);
        return new BoardResponseDto(newboard);
    }

    public List<BoardResponseDto> getAllBoard() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
    }
    public BoardResponseDto getOneBoard(Long id) {
        Board board = findById(id);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto modifyBoard(Long id, BoardRequestDto requestDto, User user) {
        //작성자 일치 확인
        Board board = findById(id);

        if (!user.getRole().equals(UserRoleEnum.ADMIN)&&!board.getUsername().equals(user.getUsername())) {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
        board.update(requestDto);
        return new BoardResponseDto(board);

    }

    public StringResponseDto deleteBoard(Long id, User user) {
        //작성자 일치 확인
        Board board = findById(id);

        if (!user.getRole().equals(UserRoleEnum.ADMIN)&&!board.getUsername().equals(user.getUsername())) {
            throw new CustomException(ErrorCode.ID_NOT_MATCH);
        }
        boardRepository.deleteById(id);
        return new StringResponseDto("삭제를 성공하였음");
    }

    @Transactional
    public StringResponseDto likeBoard(Long id, User user){
        Board board = findById(id);
        BoardLike overlap = boardLikeRepository.findByBoardAndUser(board,user);

        if(overlap!=null){
            boardLikeRepository.delete(overlap); // 좋아요 삭제
            board.unlike(); // 해당 게시물 좋아요 취소시키는 메서드
            return new StringResponseDto("좋아요 취소");
        }
        else{
            BoardLike boardLike = new BoardLike(board,user);
            boardLikeRepository.save(boardLike); // 좋아요를 누른 user/board id 저장
            board.like(); // 해당 게시물 좋아요수 증가시키는 메서드
            return new StringResponseDto("좋아요 확인");
        }
    }

    private Board findById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }
}