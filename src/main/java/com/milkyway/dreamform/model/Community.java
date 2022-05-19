package com.milkyway.dreamform.model;

import com.milkyway.dreamform.model.Timestamped;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Community extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long community_id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String community_title;

    @Column
    private String community_contents;

    @Embedded
    private UploadFile community_image;

    @Column(columnDefinition = "boolean default false", nullable = false)
    private boolean imgWhether;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer viewCounts;

    @PrePersist
    public void prePersist() {
        this.viewCounts = this.viewCounts == null ? 0 : this.viewCounts;
    }

    @Builder
    public Community(Long community_id, User user, String community_title, String community_contents,UploadFile community_image, boolean imgWhether, Integer viewCounts) {
        this.community_id = community_id;
        this.user = user;
        this.community_title = community_title;
        this.community_contents = community_contents;
        this.community_image = community_image;
        this.imgWhether = imgWhether;
        this.viewCounts = viewCounts;
    }

    public void updateImage(UploadFile community_image) {
        this.community_image = community_image;
    }
}

