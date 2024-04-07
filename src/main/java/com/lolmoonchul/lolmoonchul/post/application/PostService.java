package com.lolmoonchul.lolmoonchul.post.application;

import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.member.domain.MemberRepository;
import com.lolmoonchul.lolmoonchul.member.exception.member.MemberNotFoundException;
import com.lolmoonchul.lolmoonchul.post.application.dto.CreatePostRequest;
import com.lolmoonchul.lolmoonchul.post.application.dto.PostResponse;
import com.lolmoonchul.lolmoonchul.post.application.dto.UpdatePostRequest;
import com.lolmoonchul.lolmoonchul.post.domain.Post;
import com.lolmoonchul.lolmoonchul.post.domain.PostRepository;
import com.lolmoonchul.lolmoonchul.post.exception.NotAuthorException;
import com.lolmoonchul.lolmoonchul.post.exception.PostNotFountException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public List<PostResponse> fetchPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAllWithMember(pageable);
        return posts.stream().map(PostResponse::new)
            .collect(Collectors.toList());
    }

    public PostResponse createPost(MemberIdDto memberIdDto, CreatePostRequest createPostRequest) {
        Member member = memberRepository.findById(memberIdDto.memberId())
            .orElseThrow(() -> new MemberNotFoundException(memberIdDto.memberId()));

        Post post = postRepository.save(createPostRequest.toEntity(member));
        return new PostResponse(post);
    }

    public PostResponse updatePost(MemberIdDto memberIdDto, UpdatePostRequest postDto) {
        Post post = postRepository.findById(postDto.getPostId())
            .orElseThrow(() -> new PostNotFountException(postDto.getPostId()));

        validatePostAuthor(memberIdDto.memberId(), post.getMember().getId());
        Post updatedPost = post.update(postDto);
        return new PostResponse(updatedPost);
    }

    public void removePost(MemberIdDto memberIdDto, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFountException(postId));

        validatePostAuthor(memberIdDto.memberId(), post.getMember().getId());

        postRepository.delete(post);
    }


    private void validatePostAuthor(Long memberId, Long postMemberId) {
        if (!Objects.equals(memberId, postMemberId)) {
            throw new NotAuthorException(memberId, postMemberId);
        }
    }
}
