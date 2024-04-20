package com.lolmoonchul.lolmoonchul.vote.application;

import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.member.domain.MemberRepository;
import com.lolmoonchul.lolmoonchul.member.exception.member.MemberNotFoundException;
import com.lolmoonchul.lolmoonchul.post.domain.Post;
import com.lolmoonchul.lolmoonchul.post.domain.PostRepository;
import com.lolmoonchul.lolmoonchul.post.exception.PostNotFoundException;
import com.lolmoonchul.lolmoonchul.vote.application.dto.VoteCheckResponse;
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

    public VoteResponse voteOnPost(MemberIdDto memberIdDto, VoteRequest voteRequest) {
        validateVoteOption(voteRequest.getVoteOption());
        final String voteOption = voteRequest.getVoteOption();

        Member member = getMember(memberIdDto.memberId());
        Post post = getPost(voteRequest.getPostId());

        boolean isUserVoted = voteRepository.existsByMemberAndPost(member, post);

        if (isUserVoted) {
            return removeVote(post, member, voteOption);
        }

        return createVote(post, member, voteOption);
    }

    private VoteResponse removeVote(Post post, Member member, String voteOption) {
        int count = voteOption.equals("A") ? post.votingACancel() : post.votingBCancel();
        voteRepository.deleteByMemberAndPost(member, post);
        return new VoteResponse(count, voteOption);
    }

    private VoteResponse createVote(Post post, Member member, String voteOption) {
        int count = voteOption.equals("A") ? post.votingA() : post.votingB();
        Vote voted = new Vote(post, member, voteOption);

        voteRepository.save(voted);

        return new VoteResponse(count, voteOption);
    }

    public VoteCheckResponse isUserVotedToPost(MemberIdDto memberIdDto, long postId) {
        Member member = getMember(memberIdDto.memberId());
        Post post = getPost(postId);

        Optional<Vote> isUserVotedToPost = voteRepository.findByMemberAndPost(member, post);

        if (isUserVotedToPost.isPresent()) {
            final String voteOption = isUserVotedToPost.get().getVoteOption();
            return new VoteCheckResponse(true, voteOption);
        }

        return new VoteCheckResponse(false);
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));
    }

    private void validateVoteOption(String voteOption) {
        if (voteOption.equals("A") || voteOption.equals("B")) {
            return;
        }
        throw new InvalidVoteOptionException(voteOption);
    }
}
