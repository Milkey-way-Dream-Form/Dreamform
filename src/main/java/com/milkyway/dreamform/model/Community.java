//package com.milkyway.dreamform.model;
//
//import lombok.Builder;
//import lombok.NoArgsConstructor;
//import org.springframework.data.annotation.Id;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//
//@Entity
//@NoArgsConstructor
//public class Community extends Timestamped {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long community_id;
//
//    @Column
//    private String community_title;
//
//    @Column
//    private String community_contents;
//
//    @Column
//    private String community_image_path;
//
//    @Column
//    private String community_image_original;
//
//    @Column
//    private String user_loadmap;
//
//    @Builder
//    public Community(Long community_id, String community_title,String community_contents, String community_image_path, String community_image_original, String user_loadmap) {
//        this.community_id = community_id;
//        this.community_title = community_title;
//        this.community_contents = community_contents;
//        this.community_image_path = community_image_path;
//        this.community_image_original = community_image_original;
//        this.user_loadmap = user_loadmap;
//    }
//
//}
