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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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

    @Column(name = "content", columnDefinition = "text", nullable = true)
    private String content;

    @Column(name = "point")
    private String point;

    @Column(name = "line")
    private String line;

    @Column(name = "lol_name")
    private String lolName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Vote vote;

    public Post(String title, String content, String point, String lolName, String line,
        Member member) {
        this.title = title;
        this.content = content;
        this.point = point;
        this.lolName = lolName;
        this.line = line;
        this.member = member;
        this.vote = new Vote(this);
    }

    public Post update(UpdatePostRequest postDto) {
        this.title = postDto.getTitle();
        this.content = postDto.getContent();
        this.point = postDto.getPoint();
        this.line = postDto.getLine();
        this.lolName = postDto.getLolName();
        return this;
    }
}
