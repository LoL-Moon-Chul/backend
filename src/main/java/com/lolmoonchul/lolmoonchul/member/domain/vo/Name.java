package com.lolmoonchul.lolmoonchul.member.domain.vo;

import com.lolmoonchul.lolmoonchul.member.exception.member.NameBlankException;
import com.lolmoonchul.lolmoonchul.member.exception.member.NameLengthException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Name {

    public static final int MAX_LENGTH = 20;

    @Column(name = "name", nullable = false, length = MAX_LENGTH)
    private String value;

    public Name(final String value) {
        validateNull(value);
        final String trimmedValue = value.trim();
        validateTrim(trimmedValue);
        this.value = trimmedValue;
    }

    private void validateNull(final String value) {
        if (Objects.isNull(value)) {
            throw new NullPointerException("멤버 이름은 null일 수 없습니다.");
        }
    }

    private void validateTrim(final String value) {
        if (value.length() > MAX_LENGTH) {
            throw new NameLengthException(MAX_LENGTH, value);
        }

        if (value.isBlank()) {
            throw new NameBlankException();
        }
    }

    public Name change(final String name) {
        return new Name(name);
    }
}
