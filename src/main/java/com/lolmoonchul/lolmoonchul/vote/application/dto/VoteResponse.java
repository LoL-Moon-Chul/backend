package com.lolmoonchul.lolmoonchul.vote.application.dto;

import lombok.Getter;

@Getter
public class VoteResponse {

    int count;
    String voteOption;

    public VoteResponse(int count, String voteOption) {
        this.count = count;
        this.voteOption = voteOption;
    }
}
