package com.lolmoonchul.lolmoonchul.auth.oauth.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lolmoonchul.lolmoonchul.auth.exception.AuthenticationException.FailAuthenticationException;
import com.lolmoonchul.lolmoonchul.auth.oauth.application.dto.AccessToken;
import com.lolmoonchul.lolmoonchul.auth.oauth.application.dto.OauthMember;
import com.lolmoonchul.lolmoonchul.auth.oauth.configuration.KakaoOauthProperties;
import com.lolmoonchul.lolmoonchul.auth.oauth.util.HttpUtils;
import com.lolmoonchul.lolmoonchul.auth.oauth.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoTokenService {

    private final RestTemplate restTemplate;
    private final HttpUtils httpUtils;
    private final JsonUtils jsonUtils;
    private final ObjectMapper objectMapper;
    private final KakaoOauthProperties oauthProperties;

    private static final String PREFIX_BEARER = "Bearer ";
    private static final String HEADER_TYPE = "Authorization";

    public String fetchAccessToken(final String kakaoCode) {
        final ResponseEntity<String> response = sendAccessTokenRequest(kakaoCode);
        final AccessToken accessToken = jsonUtils.extractAccessToken(response.getBody());
        return accessToken.accessToken();
    }

    private ResponseEntity<String> sendAccessTokenRequest(final String kakaoCode) {
        try {
            return restTemplate.postForEntity(
                oauthProperties.getKakaoRequestUrl(),
                httpUtils.generateRequestEntity(kakaoCode),
                String.class);
        } catch (Exception e) {
            final String logMessage = "Oauth 통신에 실패했습니다. - " + e.getMessage();
            throw new FailAuthenticationException(logMessage);
        }
    }

    public OauthMember getOauthMember(final String accessToken) {
        ResponseEntity<String> response = fetchOauthMemberInfo(accessToken);
        log.info(response.getBody());
        OauthMember oauthMember = jsonUtils.extractOauthMember(response.getBody());
        return oauthMember;
    }

    private ResponseEntity<String> fetchOauthMemberInfo(final String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HEADER_TYPE, PREFIX_BEARER + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
            oauthProperties.getKakaoUserInfoRequestUrl(),
            HttpMethod.GET,
            entity,
            String.class);
    }


}

