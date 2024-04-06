package com.lolmoonchul.lolmoonchul.common.exception;

public class CustomBadRequestException extends RuntimeException {

    public CustomBadRequestException(final String message) {
        super(message);
    }
}
