package com.milkyway.dreamform.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
@ToString
public class Community extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long community_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "username")
    private User user;

    @Column
    private String community_title;

    @Column
    private String community_contents;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer viewCounts;

//    @Column
//    private String community_image_path;
//
//    @Column
//    private String community_image_original;
//
//    @Column
//    private String user_loadmap;

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
}
