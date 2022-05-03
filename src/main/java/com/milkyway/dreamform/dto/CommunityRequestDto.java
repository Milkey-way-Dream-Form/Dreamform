package com.milkyway.dreamform.dto;

import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommunityRequestDto {
    private Long community_id;
    private String community_title;
    private String community_contents;
//    private int viewCounts;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
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
    public CommunityRequestDto(Long community_id, String community_title, String community_contents) {
        this.community_id = community_id;
        this.community_title = community_title;
        this.community_contents = community_contents;
//        this.viewCounts = viewCounts;
    }
}
