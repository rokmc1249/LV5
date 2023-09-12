package com.nakta.springlv1.domain.board.entity;

import com.nakta.springlv1.domain.board.dto.BoardRequestDto;
import com.nakta.springlv1.domain.boardlike.entity.BoardLike;
import com.nakta.springlv1.domain.comment.entity.Comment;
import com.nakta.springlv1.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor

public class Board extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "content", nullable = false, length = 500)
    private String content;

    @Column(name = "boardLike", nullable = false)//추가
    private int boardLike;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "board",cascade = {CascadeType.REMOVE})
    private List<Comment> commentList = new ArrayList<>();


    public Board(BoardRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.username = user.getUsername();
        this.content = requestDto.getContent();
        this.user = user;
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
    public List<Comment> getCommentList() {
        return commentList;
    }
    public void unlike(){
        this.boardLike=this.boardLike-1;
    }
    public void like(){
        this.boardLike=this.boardLike+1;
    }

}
