package com.milkyway.dreamform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User extends Timestamped {

    public User(String username, String password, String nickname, String email, String interest, String license) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.interest = interest;
        this.license = license;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String interest;

    @Column(nullable = false)
    private String license;

}
