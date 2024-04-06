package com.lolmoonchul.lolmoonchul.member.exception.stat;

import com.lolmoonchul.lolmoonchul.common.exception.CustomBadRequestException;

public class StatRangeException extends CustomBadRequestException {

    public StatRangeException() {
        super("올바른 스탯 값이 아닙니다.");
    }
}
