package com.milkyway.dreamform.model;

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
    private Long community_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userName")
    private User user;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
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
    public Community(Long community_id, User user, String community_title, String community_contents, Integer viewCounts) {
        this.community_id = community_id;
        this.user = user;
        this.community_title = community_title;
        this.community_contents = community_contents;
        this.viewCounts = viewCounts;
    }

    public void addCommunity(Reply reply) {
        replies.add(reply);
        reply.setCommunity(this);
    }
}

