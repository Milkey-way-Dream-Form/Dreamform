package com.milkyway.dreamform.dto;

import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommunityDto {
    private Long community_id;
    private String userName;
    private String community_title;
    private String community_contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer viewCounts;
//
    public Community toEntity(User user) {
        Community build = Community.builder()
                .community_id(community_id)
                .user(user)
                .community_title(community_title)
                .community_contents(community_contents)
                .viewCounts(viewCounts)
                .build();
        return build;
    }

    public Page<CommunityDto> toDtoList(Page<Community> communityList) {
        Page<CommunityDto> communityDtoList = communityList.map(community -> CommunityDto.builder()
                .community_id(community.getCommunity_id())
                .userName(community.getUser().getUsername())
                .community_title(community.getCommunity_title())
                .community_contents(community.getCommunity_contents())
                .viewCounts(community.getViewCounts())
                .createdAt(community.getCreatedAt())
                .modifiedAt(community.getModifiedAt())
                .build());
        return communityDtoList;
    }

    @Builder
    public CommunityDto(Long community_id, String userName, String community_title, String community_contents, LocalDateTime createdAt, LocalDateTime modifiedAt, Integer viewCounts) {
        this.community_id = community_id;
        this.userName = userName;
        this.community_title = community_title;
        this.community_contents = community_contents;
        this.viewCounts = viewCounts;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
