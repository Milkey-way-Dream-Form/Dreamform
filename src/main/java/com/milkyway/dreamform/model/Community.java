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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long community_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String community_title;

    @Column
    private String community_contents;

//    @Column
//    private int viewCounts;

//    @Column
//    private String community_image_path;
//
//    @Column
//    private String community_image_original;
//
//    @Column
//    private String user_loadmap;

    @Builder
    public Community(Long community_id, User user, String community_title, String community_contents, int viewCounts) {
        this.community_id = community_id;
        this.user = user;
        this.community_title = community_title;
        this.community_contents = community_contents;
//        this.viewCounts = viewCounts;
    }
}
