package com.milkyway.dreamform.model;

import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Getter
@NoArgsConstructor
@Entity
public class LikeCheck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_Like_User"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id", foreignKey = @ForeignKey(name = "FK_Like_Community"))
    private Community community;

    public void mappingUser(User user) {
        this.user = user;
        user.mappingLike(this);
    }
    public void mappingCommunity(Community community) {
        this.community = community;
        community.mappingLike(this);
    }
}
