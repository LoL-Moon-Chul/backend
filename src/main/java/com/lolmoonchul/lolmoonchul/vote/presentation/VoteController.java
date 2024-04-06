package com.lolmoonchul.lolmoonchul.vote.presentation;

import com.lolmoonchul.lolmoonchul.member.configuration.AuthPrincipal;
import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import com.lolmoonchul.lolmoonchul.vote.application.VoteService;
import com.lolmoonchul.lolmoonchul.vote.application.dto.VotingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/vote")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/private")
    public ResponseEntity<Void> voting(
        @AuthPrincipal MemberIdDto memberIdDto,
        @RequestBody VotingRequest votingRequest
    ) {
        voteService.voting(memberIdDto, votingRequest);
        return ResponseEntity.ok().build();
    }
}
