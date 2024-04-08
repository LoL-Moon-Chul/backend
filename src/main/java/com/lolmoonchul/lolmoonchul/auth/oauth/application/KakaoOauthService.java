package com.lolmoonchul.lolmoonchul.auth.oauth.application;

import com.lolmoonchul.lolmoonchul.auth.jwt.JwtTokenProvider;
import com.lolmoonchul.lolmoonchul.auth.oauth.application.dto.OauthMember;
import com.lolmoonchul.lolmoonchul.auth.oauth.application.dto.Properties;
import com.lolmoonchul.lolmoonchul.auth.oauth.application.dto.TokenResponse;
import com.lolmoonchul.lolmoonchul.auth.oauth.util.RandomNameGenerate;
import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.member.domain.MemberRepository;
import com.lolmoonchul.lolmoonchul.member.domain.vo.Name;
import com.lolmoonchul.lolmoonchul.token.domain.Token;
import com.lolmoonchul.lolmoonchul.token.domain.TokenRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class KakaoOauthService {

    private static final int NAME_BEGIN_INDEX = 0;

    private final KakaoTokenService kakaoTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final RandomNameGenerate randomNameGenerate;

    public TokenResponse createToken(final String kakaoCode) {
        final String kakaoAccessToken = kakaoTokenService.fetchAccessToken(kakaoCode);
        OauthMember oauthMember = createOauthMember(kakaoAccessToken);

        final Member member = createMemberIfNotExist(oauthMember);

        final String accessToken = jwtTokenProvider.generateAccessToken(member.getId());
        final String refreshToken = jwtTokenProvider.generateRefreshToken(member.getId());

        saveOrUpdateRefreshToken(member, refreshToken);

        log.info("토큰 생성 - 사용자 아이디 : {}", member.getId());
        return new TokenResponse(accessToken, refreshToken);
    }

    private OauthMember createOauthMember(final String oauthAccessToken) {
        OauthMember oauthMember = kakaoTokenService.getOauthMember(oauthAccessToken);

        final long kakaoId = oauthMember.kakaoId();
        final String name = randomNameGenerate.generate();
        final String picture = oauthMember.properties().thumbnailImage();

        if (name.length() > Name.MAX_LENGTH) {
            final String subStringName = name.substring(NAME_BEGIN_INDEX, Name.MAX_LENGTH);
            return new OauthMember(kakaoId, new Properties(subStringName, picture));
        }
        return new OauthMember(kakaoId, new Properties(name, picture));
    }

    private Member createMemberIfNotExist(final OauthMember oauthMember) {
        Optional<Member> optionalMember = memberRepository.findByKakaoId(
            oauthMember.kakaoId());

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.isDeleted()) {
                member.comeback();
            }
            member.updateLoginTimestamp();
            return member;
        }

        final Member member = new Member(
            oauthMember.kakaoId(),
            oauthMember.properties().nickname(),
            oauthMember.properties().thumbnailImage()
        );

        return memberRepository.save(member);
    }

    private void saveOrUpdateRefreshToken(Member member, String refreshToken) {
        tokenRepository.findByMember(member)
            .ifPresentOrElse(
                token -> token.changeToken(refreshToken),
                () -> tokenRepository.save(new Token(member, refreshToken))
            );
    }
}
