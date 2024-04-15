package com.lolmoonchul.lolmoonchul.post.application;

import com.lolmoonchul.lolmoonchul.member.configuration.dto.MemberIdDto;
import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.member.domain.MemberRepository;
import com.lolmoonchul.lolmoonchul.member.exception.member.MemberNotFoundException;
import com.lolmoonchul.lolmoonchul.post.application.dto.CreatePostRequest;
import com.lolmoonchul.lolmoonchul.post.application.dto.FetchPostsResponse;
import com.lolmoonchul.lolmoonchul.post.application.dto.PostResponse;
import com.lolmoonchul.lolmoonchul.post.application.dto.UpdatePostRequest;
import com.lolmoonchul.lolmoonchul.post.domain.Post;
import com.lolmoonchul.lolmoonchul.post.domain.PostRepository;
import com.lolmoonchul.lolmoonchul.post.exception.NotAuthorException;
import com.lolmoonchul.lolmoonchul.post.exception.PostNotFoundException;
import java.util.List;
import java.util.Objects;
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
    public FetchPostsResponse fetchPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAllWithMember(pageable);
        List<PostResponse> postResponses = posts.stream().map(PostResponse::new)
            .toList();

        long totalElements = posts.getTotalElements();
        int totalPages = posts.getTotalPages();

        return new FetchPostsResponse(postResponses, totalElements, totalPages);
    }

    @Transactional(readOnly = true)
    public PostResponse fetchPost(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));
        return new PostResponse(post);
    }

    public PostResponse createPost(MemberIdDto memberIdDto, CreatePostRequest createPostRequest) {
        Member member = memberRepository.findById(memberIdDto.memberId())
            .orElseThrow(() -> new MemberNotFoundException(memberIdDto.memberId()));

        Post post = postRepository.save(createPostRequest.toEntity(member));
        return new PostResponse(post);
    }

    public PostResponse updatePost(MemberIdDto memberIdDto, UpdatePostRequest postDto) {
        Post post = postRepository.findById(postDto.getPostId())
            .orElseThrow(() -> new PostNotFoundException(postDto.getPostId()));

        validatePostAuthor(memberIdDto.memberId(), post.getMember().getId());
        Post updatedPost = post.update(postDto);
        return new PostResponse(updatedPost);
    }

    public void removePost(MemberIdDto memberIdDto, Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));

        validatePostAuthor(memberIdDto.memberId(), post.getMember().getId());

        postRepository.delete(post);
    }


    private void validatePostAuthor(Long memberId, Long postMemberId) {
        if (!Objects.equals(memberId, postMemberId)) {
            throw new NotAuthorException(memberId, postMemberId);
        }
    }
}
