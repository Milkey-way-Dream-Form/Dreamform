package com.milkyway.dreamform.service;

import com.milkyway.dreamform.dto.CommunityDto;
import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.repository.CommunityRepository;
import com.milkyway.dreamform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;

    public List<CommunityDto> getCommunityList() {
        log.info("" + communityRepository.findAll());
        List<Community> communities = communityRepository.findAll();
        List<CommunityDto> communityList = new ArrayList<>();
        for(Community community : communities) {
            CommunityDto communityDto = CommunityDto.builder()
                    .community_id(community.getCommunity_id())
                    .userName(community.getUser().getUsername())
                    .community_title(community.getCommunity_title())
                    .community_contents(community.getCommunity_contents())
                    .createdAt(community.getCreatedAt())
                    .modifiedAt(community.getModifiedAt())
                    .build();
            communityList.add(communityDto);
            log.info("" + communityDto.getCommunity_id());
            log.info("" + communityDto.getCommunity_title());
            log.info("" + communityDto.getCommunity_contents());
            log.info("" + communityDto.getCreatedAt());
        }
        return communityList;
    }

    @Transactional
    public void CreatePost(String userName, CommunityDto communityDto) throws IOException {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다. " + userName)
        );
        communityRepository.save(communityDto.toEntity(user)).getCommunity_id();
    }
}