package com.lolmoonchul.lolmoonchul.vote.application.dto;

import lombok.Getter;

@Getter
public class VoteRequest {

    Long postId;
    String voteOption;
}
