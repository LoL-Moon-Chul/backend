package com.lolmoonchul.lolmoonchul.vote.exception;

import com.lolmoonchul.lolmoonchul.common.exception.CustomBadRequestException;

public class InvalidVoteOptionException extends CustomBadRequestException {

    public InvalidVoteOptionException(final String voteOption) {
        super(String.format("유효한 투표 선택지가 아닙니다. - reqeust info : {}", voteOption));
    }
}
