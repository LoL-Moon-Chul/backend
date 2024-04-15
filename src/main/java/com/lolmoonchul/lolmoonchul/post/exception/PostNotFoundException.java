package com.lolmoonchul.lolmoonchul.post.exception;

import com.lolmoonchul.lolmoonchul.common.exception.CustomNotFoundException;

public class PostNotFoundException extends CustomNotFoundException {

    public PostNotFoundException(final Long postId) {
        super(String.format("게시글을 찾을 수 없습니다. - request info postId : %d", postId));
    }
}
