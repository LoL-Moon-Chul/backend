package com.lolmoonchul.lolmoonchul.post.presentation;

import com.lolmoonchul.lolmoonchul.member.configuration.AuthPrincipal;
import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import com.lolmoonchul.lolmoonchul.post.application.PostService;
import com.lolmoonchul.lolmoonchul.post.application.dto.CreatePostRequest;
import com.lolmoonchul.lolmoonchul.post.application.dto.PostResponse;
import com.lolmoonchul.lolmoonchul.post.application.dto.UpdatePostRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/public")
    public ResponseEntity<List<PostResponse>> fetchPosts(Pageable pageable) {
        List<PostResponse> postResponses = postService.fetchPosts(pageable);
        return ResponseEntity.ok().body(postResponses);
    }

    @PostMapping("/private")
    public ResponseEntity<PostResponse> createPost(
        @AuthPrincipal MemberIdDto memberIdDto,
        @RequestBody CreatePostRequest postRequest
    ) {
        PostResponse postResponse = postService.createPost(memberIdDto, postRequest);
        return ResponseEntity.ok().body(postResponse);
    }

    @PutMapping("/private")
    public ResponseEntity<PostResponse> updatePost(
        @AuthPrincipal MemberIdDto memberIdDto,
        @RequestBody UpdatePostRequest postRequest
    ) {
        PostResponse postResponse = postService.updatePost(memberIdDto, postRequest);
        return ResponseEntity.ok().body(postResponse);
    }

    @DeleteMapping("/private/{postId}")
    public ResponseEntity<Void> removePost(
        @AuthPrincipal MemberIdDto memberIdDto,
        @PathVariable Long postId
    ) {
        postService.removePost(memberIdDto, postId);
        return ResponseEntity.noContent().build();
    }
}
