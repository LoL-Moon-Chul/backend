package com.lolmoonchul.lolmoonchul.post.application.dto;

import com.lolmoonchul.lolmoonchul.post.domain.Post;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class FetchPostResponse {

    Long memberId;
    String memberName;
    String memberImageUrl;
    Long postId;
    String title;
    String content;
    String point;
    String lineA;
    String lineB;
    int voteA;
    int voteB;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdAt;

    public FetchPostResponse(Post post) {
        this.memberId = post.getMember().getId();
        this.memberName = post.getMember().getName().getValue();
        this.memberImageUrl = post.getMember().getProfileImageUrl();
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.point = post.getPoint();
        this.lineA = post.getLineA();
        this.lineB = post.getLineB();
        this.voteA = post.getVoteA();
        this.voteB = post.getVoteB();
        this.createdAt = post.getCreatedAt();
    }
}
