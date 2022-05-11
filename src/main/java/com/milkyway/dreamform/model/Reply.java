package com.milkyway.dreamform.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@NoArgsConstructor
@Getter @Setter
public class Reply extends Timestamped {
        @Id
        @GeneratedValue
        @Column(name = "reply_id")
        private Long id;

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "community_id")
        private Community community;

        private String comment;

        public void updateReply(String comment) {
                this.comment = comment;
        }
}
