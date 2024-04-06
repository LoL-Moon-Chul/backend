package com.lolmoonchul.lolmoonchul.token.presentation;

import com.lolmoonchul.lolmoonchul.auth.jwt.JwtTokenExtractor;
import com.lolmoonchul.lolmoonchul.token.application.TokenService;
import com.lolmoonchul.lolmoonchul.token.application.dto.TokenResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {

    private final JwtTokenExtractor jwtTokenExtractor;
    private final TokenService tokenService;

    @PostMapping("/public/reissue")
    public ResponseEntity<TokenResponse> reissueAccessToken(
        final HttpServletRequest request
    ) {
        String refreshToken = jwtTokenExtractor.extractRefreshToken(request);
        TokenResponse tokenResponse = tokenService.reissueAccessToken(refreshToken);
        return ResponseEntity.ok().body(tokenResponse);
    }
}
