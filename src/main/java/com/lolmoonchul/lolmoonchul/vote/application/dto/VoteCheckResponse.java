package com.lolmoonchul.lolmoonchul.vote.application.dto;

import lombok.Getter;

@Getter
public class VoteCheckResponse {

    boolean isUserVoted;

    public VoteCheckResponse(boolean isUserVoted) {
        this.isUserVoted = isUserVoted;
    }
}
