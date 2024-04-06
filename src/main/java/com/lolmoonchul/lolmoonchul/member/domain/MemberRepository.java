package com.lolmoonchul.lolmoonchul.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByKakaoId(Long kakaoId);

    Optional<Member> findByKakaoId(final long kakaoId);

}
