package com.milkyway.dreamform.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends Timestamped {

//    public User(String username, String password, String email,String filed) {
//        this.username = username;
//        this.password = password;
//        this.email = email;
//        this.kakaoId = null;
//        this.filed = filed;
//    }
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.kakaoId = null;
    }

    public User(String username, String password, String email, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.kakaoId = kakaoId;
    }

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String filed;

    @Column(nullable = true)
    private Long kakaoId;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    private List<Reply> replies = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<LikeCheck> likeList = new ArrayList<>();

    //비밀번호 변경
    public void updatePw(String pw) {
        this.password = pw;
    }

    //댓글 추가
    public void addReply(Reply reply) {
        replies.add(reply);
        reply.setUser(this);
    }

    //댓글 삭제
    public void deleteReply(Reply reply) {
        replies.remove(reply);
        reply.setUser(null);
    }

    public void mappingLike(LikeCheck like) {
        this.likeList.add(like);
    }

}
