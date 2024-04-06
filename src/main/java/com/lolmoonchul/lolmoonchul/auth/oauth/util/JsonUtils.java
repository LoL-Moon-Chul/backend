package com.lolmoonchul.lolmoonchul.auth.oauth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lolmoonchul.lolmoonchul.auth.exception.AuthenticationException.FailAuthenticationException;
import com.lolmoonchul.lolmoonchul.auth.oauth.application.dto.AccessToken;
import com.lolmoonchul.lolmoonchul.auth.oauth.application.dto.OauthMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonUtils {

    private final ObjectMapper objectMapper;

    public AccessToken extractAccessToken(String jsonValue) {
        try {
            return objectMapper.readValue(jsonValue, AccessToken.class);
        } catch (JsonProcessingException e) {
            final String logMessage =
                "카카오 accessToken Json 파싱에 실패했습니다. response body : " + jsonValue;
            throw new FailAuthenticationException(logMessage);
        }
    }

    public OauthMember extractOauthMember(String jsonValue) {
        try {
            return objectMapper.readValue(jsonValue, OauthMember.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("카카오 유저 정보 파싱에 실패했습니다. - ", e);
        }
    }
}