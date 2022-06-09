package com.milkyway.dreamform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Community extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reply>  replies = new ArrayList<>();

    @Column
    private String community_title;

    @Column
    private String community_contents;

    @Embedded
    private UploadFile uploadFile;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean imgWhether;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer viewCounts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<LikeCheck> likeList = new ArrayList<>();

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer likeCounts;

    @PrePersist
    public void prePersist() {
        this.viewCounts = this.viewCounts == null ? 0 : this.viewCounts;
        this.likeCounts = this.likeCounts == null ? 0 : this.likeCounts;
    }

    @Builder
    public Community(Long id, User user, String community_title, String community_contents,UploadFile uploadFile, boolean imgWhether, Integer viewCounts, Integer likeCounts) {
        this.id = id;
        this.user = user;
        this.community_title = community_title;
        this.community_contents = community_contents;
        this.uploadFile = uploadFile;
        this.imgWhether = imgWhether;
        this.viewCounts = viewCounts;
        this.likeCounts = likeCounts;
    }


    public void updateImage(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }
    //댓글 추가
    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setCommunity(this);
    }

    //댓글 삭제
    public void deleteReply(Reply reply) {
        replies.remove(reply);
        reply.setCommunity(null);

    }

    public void mappingLike(LikeCheck like) {
        this.likeList.add(like);
    }

    public void updateLikeCount() {
        this.likeCounts = this.likeList.size();
    }

    public void disCountLike(LikeCheck like) {
        this.likeList.remove(like);
    }
}

