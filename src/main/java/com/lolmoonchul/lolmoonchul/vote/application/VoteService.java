package com.lolmoonchul.lolmoonchul.vote.application;

import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.member.domain.MemberRepository;
import com.lolmoonchul.lolmoonchul.member.exception.member.MemberNotFoundException;
import com.lolmoonchul.lolmoonchul.post.domain.Post;
import com.lolmoonchul.lolmoonchul.post.domain.PostRepository;
import com.lolmoonchul.lolmoonchul.post.exception.PostNotFountException;
import com.lolmoonchul.lolmoonchul.vote.application.dto.VoteRequest;
import com.lolmoonchul.lolmoonchul.vote.application.dto.VoteResponse;
import com.lolmoonchul.lolmoonchul.vote.domain.Vote;
import com.lolmoonchul.lolmoonchul.vote.domain.VoteRepository;
import com.lolmoonchul.lolmoonchul.vote.exception.InvalidVoteOptionException;
import java.util.Optional;
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

    public VoteResponse voting(MemberIdDto memberIdDto, VoteRequest voteRequest) {
        validateVoteOption(voteRequest.getVoteOption());
        final String voteOption = voteRequest.getVoteOption();

        Member member = memberRepository.findById(memberIdDto.memberId())
            .orElseThrow(() -> new MemberNotFoundException(memberIdDto.memberId()));

        Post post = postRepository.findById(voteRequest.getPostId())
            .orElseThrow(() -> new PostNotFountException(voteRequest.getPostId()));

        Optional<Vote> optionalVote = voteRepository.findByMemberAndPost(member, post);

        if (optionalVote.isPresent()) {
            int count = voteOption.equals("A") ? post.votingACancel() : post.votingBCancel();
            voteRepository.deleteByMemberAndPost(member, post);
            return new VoteResponse(count, voteOption);
        }

        Vote voted = new Vote(post, member);
        int count = voteOption.equals("A") ? post.votingA() : post.votingB();
        voteRepository.save(voted);

        return new VoteResponse(count, voteOption);
    }

    private void validateVoteOption(String voteOption) {
        if (voteOption.equals("A") || voteOption.equals("B")) {
            return;
        }
        throw new InvalidVoteOptionException(voteOption);
    }
}
