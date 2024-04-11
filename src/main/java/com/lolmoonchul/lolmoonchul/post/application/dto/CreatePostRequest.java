package com.lolmoonchul.lolmoonchul.post.application.dto;

import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.post.domain.Post;
import lombok.Getter;

@Getter
public class CreatePostRequest {

    String title;
    String content;
    String point;
    String lolName;
    String lineA;
    String lineB;

    public Post toEntity(Member member) {
        return new Post(title, content, point, lolName, lineA, lineB, member);
    }
}
