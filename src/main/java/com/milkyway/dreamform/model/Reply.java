package com.milkyway.dreamform.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Reply extends Timestamped {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "reply_id")
        private Long id;

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne(fetch = LAZY)
        @JoinColumn(name = "community_id")
        private Community community;

        private String comment;

        public static Reply createReply(User user, Community community) {
                Reply reply = new Reply();
                reply.setUser(user);
                reply.setCommunity(community);
                return reply;
        }
        public void updateReply(String comment) {
                this.comment = comment;
        }
}
