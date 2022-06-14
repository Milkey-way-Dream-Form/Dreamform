package com.milkyway.dreamform.dto;

import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.UploadFile;
import com.milkyway.dreamform.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Setter
@Getter
@NoArgsConstructor
public class CommunityDto {
    private Long id;
    private String userName;
    private String community_title;
    private MultipartFile attachFile;
    private UploadFile uploadFile;
    private boolean imgWhether;
    private String community_contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Integer viewCounts;
    private Integer likeCounts;

    public Community toEntity(UploadFile uploadFile, User user, boolean imgWhether) {
        Community build = Community.builder()
                .id(id)
                .user(user)
                .community_title(community_title)
                .uploadFile(uploadFile)
                .imgWhether(imgWhether)
                .community_contents(community_contents)
                .viewCounts(viewCounts)
                .likeCounts(likeCounts)
                .build();
        log.info("UploadFile Check" + build.getUploadFile());
        return build;
    }

    public Page<CommunityDto> toDtoList(Page<Community> communityList) {
        Page<CommunityDto> communityDtoList = communityList.map(community -> CommunityDto.builder()
                .id(community.getId())
                .userName(community.getUser().getUsername())
                .community_title(community.getCommunity_title())
                .community_contents(community.getCommunity_contents())
                .uploadFile(community.getUploadFile())
                .viewCounts(community.getViewCounts())
                .likeCounts(community.getLikeCounts())
                .imgWhether(community.isImgWhether())
                .createdAt(community.getCreatedAt())
                .modifiedAt(community.getModifiedAt())
                .build());
        return communityDtoList;
    }

    public List<CommunityDto> toBestDtoList(List<Community> communityList) {
        List<CommunityDto> communityDtoList = new ArrayList<>();
        for(Community community : communityList) {
            CommunityDto communityDto = CommunityDto.builder()
                    .id(community.getId())
                    .userName(community.getUser().getUsername())
                    .community_title(community.getCommunity_title())
                    .community_contents(community.getCommunity_contents())
                    .uploadFile(community.getUploadFile())
                    .viewCounts(community.getViewCounts())
                    .likeCounts(community.getLikeCounts())
                    .imgWhether(community.isImgWhether())
                    .createdAt(community.getCreatedAt())
                    .modifiedAt(community.getModifiedAt())
                    .build();
            communityDtoList.add(communityDto);
        }
        return communityDtoList;
    }

    @Builder
    public CommunityDto(Long id, String userName, String community_title, UploadFile uploadFile, MultipartFile attachFile, String community_contents,Boolean imgWhether, LocalDateTime createdAt, LocalDateTime modifiedAt, Integer viewCounts, Integer likeCounts) {
        this.id = id;
        this.userName = userName;
        this.community_title = community_title;
        this.uploadFile = uploadFile;
        this.attachFile = attachFile;
        this.imgWhether = imgWhether;
        this.community_contents = community_contents;
        this.viewCounts = viewCounts;
        this.likeCounts = likeCounts;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}
