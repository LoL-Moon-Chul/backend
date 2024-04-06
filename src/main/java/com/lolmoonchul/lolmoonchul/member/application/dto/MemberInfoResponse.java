package com.lolmoonchul.lolmoonchul.member.application.dto;

import com.lolmoonchul.lolmoonchul.member.domain.Member;

public record MemberInfoResponse(
    Long id,
    Long kakaoId,
    String name,
    String profileImageUrl
) {

    public static MemberInfoResponse of(final Member member) {
        return new MemberInfoResponse(
            member.getId(),
            member.getKakaoId(),
            member.getName().getValue(),
            member.getProfileImageUrl()
        );
    }
}
