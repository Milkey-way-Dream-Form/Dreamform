package com.milkyway.dreamform.dto;

import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommunityDto {
    private Long community_id;
    private String userName;
    private String community_title;
    private String community_contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    //    private int viewCounts;
//    private String community_image_path;
//    private String community_image_original;
//    private String user_loadmap;

    public Community toEntity(User user) {
        Community build = Community.builder()
                .community_id(community_id)
                .user(user)
                .community_title(community_title)
                .community_contents(community_contents)
//                .viewCounts(viewCounts)
                .build();
        return build;
    }

    @Builder
    public CommunityDto(Long community_id, String userName, String community_title, String community_contents, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.community_id = community_id;
        this.userName = userName;
        this.community_title = community_title;
        this.community_contents = community_contents;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
//        this.viewCounts = viewCounts;
    }
}
