package com.lolmoonchul.lolmoonchul.common.exception;

public class CustomUnauthorizedException extends RuntimeException {

    public CustomUnauthorizedException(final String message) {
        super(message);
    }
}
