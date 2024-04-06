package com.lolmoonchul.lolmoonchul.post.exception;

import com.lolmoonchul.lolmoonchul.common.exception.CustomBadRequestException;

public class NotAuthorException extends CustomBadRequestException {

    public NotAuthorException(final Long memberId, final Long postMemberId) {
        super(String.format("게시글 작성자가 아닙니다. - request info memberId : %d, postMemberId : %d",
            memberId, postMemberId));
    }
}
