package com.nakta.springlv1.domain.comment.controller;

import com.nakta.springlv1.domain.comment.dto.CommentRequestDto;
import com.nakta.springlv1.domain.comment.dto.CommentResponseDto;
import com.nakta.springlv1.domain.comment.service.CommentService;
import com.nakta.springlv1.domain.user.dto.StringResponseDto;
import com.nakta.springlv1.domain.user.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;
    @PostMapping("/comment")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.createComment(requestDto, userDetails.getUser());
    }
    

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDto> modifyComment(@PathVariable Long id,@RequestBody CommentRequestDto requestDto,@AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.ok(commentService.modifyComment(id,requestDto,userDetails.getUser()));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<StringResponseDto> deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.deleteComment(id,userDetails.getUser()));
    }

    @GetMapping("/comment/like/{id}")
    public ResponseEntity<StringResponseDto> likeComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(commentService.likeComment(id,userDetails.getUser()));
    }
}
