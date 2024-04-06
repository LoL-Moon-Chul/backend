package com.lolmoonchul.lolmoonchul.post.application.dto;

import com.lolmoonchul.lolmoonchul.post.domain.Post;
import lombok.Getter;

@Getter
public class PostResponse {

    Long memberId;
    String memberName;
    String memberImageUrl;
    String title;
    String point;
    int voteA;
    int voteB;

    public PostResponse(Post post) {
        this.memberId = post.getMember().getId();
        this.memberName = post.getMember().getName().getValue();
        this.memberImageUrl = post.getMember().getProfileImageUrl();
        this.title = post.getTitle();
        this.point = post.getPoint();
        this.voteA = post.getVote().getVoteA();
        this.voteB = post.getVote().getVoteB();
    }
}
