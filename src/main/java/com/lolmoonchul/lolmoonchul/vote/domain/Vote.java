package com.lolmoonchul.lolmoonchul.vote.domain;

import com.lolmoonchul.lolmoonchul.common.domain.BaseEntity;
import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote")
@Entity
public class Vote extends BaseEntity {

    @Id
    private Long id;

    @Column(name = "vote_a")
    private int voteA = 0;

    @Column(name = "vote_b")
    private int voteB = 0;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Vote(Post post) {
        this.post = post;
    }

    public void votingA() {
        this.voteA += 1;
    }

    public void votingB() {
        this.voteB += 1;
    }
}
