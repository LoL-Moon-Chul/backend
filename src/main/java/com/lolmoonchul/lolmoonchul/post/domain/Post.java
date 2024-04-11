package com.lolmoonchul.lolmoonchul.post.domain;

import com.lolmoonchul.lolmoonchul.common.domain.BaseEntity;
import com.lolmoonchul.lolmoonchul.member.domain.Member;
import com.lolmoonchul.lolmoonchul.post.application.dto.UpdatePostRequest;
import com.lolmoonchul.lolmoonchul.vote.domain.Vote;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "point")
    private String point;

    @Column(name = "line_a")
    private String lineA;

    @Column(name = "line_b")
    private String lineB;

    @Column(name = "lol_name")
    private String lolName;

    @Column(name = "vote_a")
    private int voteA = 0;

    @Column(name = "vote_b")
    private int voteB = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vote> vote = new ArrayList<>();

    public Post(
        String title,
        String content,
        String point,
        String lolName,
        String lineA,
        String lineB,
        Member member) {
        this.title = title;
        this.content = content;
        this.point = point;
        this.lolName = lolName;
        this.lineA = lineA;
        this.lineB = lineB;
        this.member = member;
    }

    public Post update(UpdatePostRequest postDto) {
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.point = postDto.getPoint();
        this.lineA = postDto.getLineA();
        this.lineB = postDto.getLineB();
        this.lolName = postDto.getLolName();
        return this;
    }

    public int votingA() {
        this.voteA += 1;
        return this.voteA;
    }

    public int votingB() {
        this.voteB += 1;
        return this.voteB;
    }

    public int votingACancel() {
        this.voteA -= 1;
        return this.voteA;
    }

    public int votingBCancel() {
        this.voteB -= 1;
        return this.voteB;
    }
}
