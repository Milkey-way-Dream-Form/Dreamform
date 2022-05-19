package com.milkyway.dreamform.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Reply extends Timestamped {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "reply_id")
        private Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private User user;

        @ManyToOne
        @JoinColumn(name = "community_id")
        private Community community;

        private String comment;

        public void updateReply(String comment) {
                this.comment = comment;
        }
}
