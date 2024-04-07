package com.lolmoonchul.lolmoonchul.vote.domain;

import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.post.domain.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    boolean existsByMemberAndPost(Member member, Post post);

    void deleteByMemberAndPost(Member member, Post post);

    Optional<Vote> findByMemberAndPost(Member member, Post post);
}
