package com.milkyway.dreamform.service;

import com.milkyway.dreamform.dto.CommunityDto;
import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.UploadFile;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.repository.CommunityRepository;
import com.milkyway.dreamform.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.security.Principal;

@AllArgsConstructor
@Slf4j
@Service
public class CommunityService {
    private CommunityRepository communityRepository;
    private UserRepository userRepository;
    private ImageService imageService;

    public Page<Community> getCommunityList(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1,
                pageable.getPageSize());
        return communityRepository.findAll(pageable);
    }

    @Transactional
    public Long createCommunity(UploadFile image,String userName, CommunityDto communityDto) throws IOException {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다. " + userName)
        );
        if(image != null) {
            return communityRepository.save(communityDto.toEntity(image, user, true)).getCommunity_id();
        }
        else {
            return communityRepository.save(communityDto.toEntity(image, user, false)).getCommunity_id();
        }
    }

    @Transactional
    public CommunityDto getCommunity(Long id){
        Community community = communityRepository.findById(id).get();
        CommunityDto communityDto = CommunityDto.builder()
                .community_id(community.getCommunity_id())
                .userName(community.getUser().getUsername())
                .community_title(community.getCommunity_title())
                .uploadFile(community.getCommunity_image())
                .community_contents(community.getCommunity_contents())
                .imgWhether(community.isImgWhether())
                .viewCounts(community.getViewCounts())
                .createdAt(community.getCreatedAt())
                .modifiedAt(community.getModifiedAt())
                .build();
        return communityDto;
    }

    @Transactional
    public int updateViewCounts(Long id) {
        return communityRepository.updateViewCounts(id);
    }

    public boolean idCheck(Principal principal, String userName) {
        return principal.getName().equals(userName);
    }

    @Transactional
    public void deleteCommunity(Long id) {
        communityRepository.deleteById(id);
    }

}

