package com.lolmoonchul.lolmoonchul.member.exception.member;

import com.lolmoonchul.lolmoonchul.common.exception.CustomNotFoundException;

public class MemberNotFoundException extends CustomNotFoundException {

    public MemberNotFoundException(final Long memberId) {
        super(String.format("조회한 멤버가 존재하지 않습니다. - request info { memberId : %d }", memberId));
    }
}
