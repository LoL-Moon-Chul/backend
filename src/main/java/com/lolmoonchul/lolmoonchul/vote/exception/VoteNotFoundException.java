package com.lolmoonchul.lolmoonchul.vote.exception;

import com.lolmoonchul.lolmoonchul.common.exception.CustomNotFoundException;

public class VoteNotFoundException extends CustomNotFoundException {

    public VoteNotFoundException(final Long voteId) {
        super(String.format("투표를 찾을 수 없습니다. - request Info voteId : %d", voteId));
    }
}
