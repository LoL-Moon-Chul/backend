package com.lolmoonchul.lolmoonchul.vote.presentation;

import com.lolmoonchul.lolmoonchul.member.configuration.AuthPrincipal;
import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import com.lolmoonchul.lolmoonchul.vote.application.VoteService;
import com.lolmoonchul.lolmoonchul.vote.application.dto.VoteCheckResponse;
import com.lolmoonchul.lolmoonchul.vote.application.dto.VoteRequest;
import com.lolmoonchul.lolmoonchul.vote.application.dto.VoteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/votes")
public class VoteController {

    private final VoteService voteService;

    @PostMapping("/private")
    public ResponseEntity<VoteResponse> voting(
        @AuthPrincipal MemberIdDto memberIdDto,
        @RequestBody VoteRequest voteRequest
    ) {
        VoteResponse voting = voteService.voteOnPost(memberIdDto, voteRequest);
        return ResponseEntity.ok().body(voting);
    }

    @GetMapping("/private/check/posts/{postId}")
    public ResponseEntity<VoteCheckResponse> checkVotedToPost(
        @AuthPrincipal MemberIdDto memberIdDto,
        @PathVariable Long postId) {
        VoteCheckResponse voteCheckResponse = voteService.isUserVotedToPost(memberIdDto, postId);
        return ResponseEntity.ok().body(voteCheckResponse);
    }
}
