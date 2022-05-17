package com.milkyway.dreamform.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.milkyway.dreamform.model.Timestamped;
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
//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userName")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reply>  replies = new ArrayList<>();

    @Column
    private String community_title;

    @Column
    private String community_contents;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer viewCounts;

    @PrePersist
    public void prePersist() {
        this.viewCounts = this.viewCounts == null ? 0 : this.viewCounts;
    }

    @Builder
    public Community(Long id, User user, String community_title, String community_contents, Integer viewCounts) {
        this.id = id;
        this.user = user;
        this.community_title = community_title;
        this.community_contents = community_contents;
        this.viewCounts = viewCounts;
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
}

