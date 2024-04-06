package com.lolmoonchul.lolmoonchul.member.presentation;

import com.lolmoonchul.lolmoonchul.member.application.MemberService;
import com.lolmoonchul.lolmoonchul.member.application.dto.MemberInfoResponse;
import com.lolmoonchul.lolmoonchul.member.configuration.AuthPrincipal;
import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/private")
    public ResponseEntity<MemberInfoResponse> getMyInformation(
        @AuthPrincipal final MemberIdDto memberIdDto
    ) {
        final MemberInfoResponse memberInfoResponse = memberService.getMemberInformation(
            memberIdDto);

        return ResponseEntity.ok(memberInfoResponse);
    }

    @DeleteMapping("/private")
    public ResponseEntity<Void> deleteAccount(
        @AuthPrincipal final MemberIdDto memberIdDto
    ) {
        memberService.leaveMember(memberIdDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
