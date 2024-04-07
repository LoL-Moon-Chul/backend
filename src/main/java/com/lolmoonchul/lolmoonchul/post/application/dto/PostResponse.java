package com.lolmoonchul.lolmoonchul.post.application.dto;

import com.lolmoonchul.lolmoonchul.post.domain.Post;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class PostResponse {

    Long memberId;
    String memberName;
    String memberImageUrl;
    Long postId;
    String title;
    String point;
    int voteA;
    int voteB;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdAt;

    public PostResponse(Post post) {
        this.memberId = post.getMember().getId();
        this.memberName = post.getMember().getName().getValue();
        this.memberImageUrl = post.getMember().getProfileImageUrl();
        this.postId = post.getId();
        this.title = post.getTitle();
        this.point = post.getPoint();
        this.voteA = post.getVoteA();
        this.voteB = post.getVoteB();
        this.createdAt = post.getCreatedAt();
    }
}
