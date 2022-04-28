package com.milkyway.dreamform.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Reply extends Timestamped {
        @Id
        @GeneratedValue
        @Column(name = "reply_id")
        private Long id;

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        private String reply_contents;
        private String reply_image_path;
        private String reply_image_original;
}
