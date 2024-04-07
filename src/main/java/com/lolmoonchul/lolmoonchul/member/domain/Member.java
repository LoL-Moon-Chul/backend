package com.lolmoonchul.lolmoonchul.member.domain;

import com.lolmoonchul.lolmoonchul.common.domain.BaseEntity;
import com.lolmoonchul.lolmoonchul.member.domain.vo.Name;
import com.lolmoonchul.lolmoonchul.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kakao_id", nullable = false, unique = true)
    private long kakaoId;

    @Embedded
    private Name name;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    public Member(
        final long kakaoId,
        final Name name,
        final String profileImageUrl
    ) {
        this.kakaoId = kakaoId;
        this.name = name;
        this.profileImageUrl = profileImageUrl;
    }

    public Member(
        final long kakaoId,
        final String name,
        final String profileImageUrl
    ) {
        this.kakaoId = kakaoId;
        this.name = new Name(name);
        this.profileImageUrl = profileImageUrl;
    }

    public void updateLoginTimestamp() {
        this.updatedAt = LocalDateTime.now();
    }

    public void leave() {
        this.isDeleted = true;
    }

    public void comeback() {
        this.isDeleted = false;
    }

}
