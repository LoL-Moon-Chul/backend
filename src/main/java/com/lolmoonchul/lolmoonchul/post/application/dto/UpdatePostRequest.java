package com.lolmoonchul.lolmoonchul.post.application.dto;

import lombok.Getter;

@Getter
public class UpdatePostRequest {

    Long postId;
    String title;
    String content;
    String point;
    String lolName;
    String line;

}
