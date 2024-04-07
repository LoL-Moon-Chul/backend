package com.lolmoonchul.lolmoonchul.member.application;

import com.lolmoonchul.lolmoonchul.member.application.dto.MemberInfoResponse;
import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.member.domain.MemberRepository;
import com.lolmoonchul.lolmoonchul.member.exception.member.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInformation(final MemberIdDto memberIdDto) {
        final Long memberId = memberIdDto.memberId();

        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException(memberId));
        return MemberInfoResponse.of(member);
    }

    public void leaveMember(final MemberIdDto memberIdDto) {
        final Long memberId = memberIdDto.memberId();

        final Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException(memberId));

        member.leave();

        log.info(String.format("사용자 회원 탈퇴 - request info { memberId : %d }", memberId));
    }
}
