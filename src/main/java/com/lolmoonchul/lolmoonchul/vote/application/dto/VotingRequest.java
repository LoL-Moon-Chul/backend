package com.lolmoonchul.lolmoonchul.vote.application.dto;

import lombok.Getter;

@Getter
public class VotingRequest {

    Long postId;
    String voteOption;
}
