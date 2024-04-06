package com.lolmoonchul.lolmoonchul.vote.application;

import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.member.domain.MemberRepository;
import com.lolmoonchul.lolmoonchul.member.exception.member.MemberNotFoundException;
import com.lolmoonchul.lolmoonchul.post.domain.Post;
import com.lolmoonchul.lolmoonchul.post.domain.PostRepository;
import com.lolmoonchul.lolmoonchul.post.exception.PostNotFountException;
import com.lolmoonchul.lolmoonchul.vote.application.dto.VotingRequest;
import com.lolmoonchul.lolmoonchul.vote.domain.Vote;
import com.lolmoonchul.lolmoonchul.vote.domain.VoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class VoteService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final VoteRepository voteRepository;

    public void voting(MemberIdDto memberIdDto, VotingRequest votingRequest) {
        Member member = memberRepository.findById(memberIdDto.memberId())
            .orElseThrow(() -> new MemberNotFoundException(memberIdDto.memberId()));

        Post post = postRepository.findById(votingRequest.getPostId())
            .orElseThrow(() -> new PostNotFountException(votingRequest.getPostId()));

        boolean voteExists = voteRepository.existsByMemberAndPost(member, post);

        if (voteExists) {
            voteRepository.deleteByMemberAndPost(member, post);
            return;
        }

        Vote vote = post.getVote();
        final String voteOption = votingRequest.getVoteOption();

        if ("A".equals(voteOption)) {
            vote.votingA();
            return;
        }

        if ("B".equals(voteOption)) {
            vote.votingB();
            return;
        }

        throw new IllegalArgumentException("Invalid vote option");
    }
}
