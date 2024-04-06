package com.lolmoonchul.lolmoonchul.auth.presentation;

import com.lolmoonchul.lolmoonchul.auth.exception.AuthenticationException;
import com.lolmoonchul.lolmoonchul.auth.jwt.JwtTokenExtractor;
import com.lolmoonchul.lolmoonchul.auth.jwt.JwtTokenProvider;
import com.lolmoonchul.lolmoonchul.member.domain.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class MemberInterceptor implements HandlerInterceptor {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        final String accessToken = jwtTokenExtractor.extractAccessToken(request);
        Long kakaoId = jwtTokenProvider.extractMemberIdFromAccessToken(accessToken);
        validateMemberExist(kakaoId);
        return true;
    }

    private void validateMemberExist(final Long kakaoId) {
        if (notExistByKakaoId(kakaoId)) {
            String logMessage = "인증 실패(존재하지 않는 멤버) - 회원 카카오ID : " + kakaoId;
            throw new AuthenticationException.FailAuthenticationException(logMessage);
        }
    }

    private boolean notExistByKakaoId(Long kakaoId) {
        return !memberRepository.existsByKakaoId(kakaoId);
    }
}
