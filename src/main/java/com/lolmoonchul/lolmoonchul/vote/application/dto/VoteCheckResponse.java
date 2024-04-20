package com.lolmoonchul.lolmoonchul.vote.application.dto;

import lombok.Getter;

@Getter
public class VoteCheckResponse {

    boolean isUserVoted;
    String voteOption;

    public VoteCheckResponse(boolean isUserVoted) {
        this.isUserVoted = isUserVoted;
        this.voteOption = null;
    }

    public VoteCheckResponse(boolean isUserVoted, String voteOption) {
        this.isUserVoted = isUserVoted;
        this.voteOption = voteOption;
    }
}
