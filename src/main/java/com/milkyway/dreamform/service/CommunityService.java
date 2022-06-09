package com.milkyway.dreamform.service;

import com.milkyway.dreamform.dto.CommunityDto;
import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.LikeCheck;
import com.milkyway.dreamform.model.UploadFile;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.repository.CommunityRepository;
import com.milkyway.dreamform.repository.LikeCheckRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class CommunityService {
    private CommunityRepository communityRepository;
    private UserRepository userRepository;
    private ImageService imageService;
    private LikeCheckRepository likeCheckRepository;


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
            return communityRepository.save(communityDto.toEntity(image, user, true)).getId();
        }
        else {
            return communityRepository.save(communityDto.toEntity(image, user, false)).getId();
        }
    }

    @Transactional
    public CommunityDto getCommunity(Long id) throws IOException{
        Community community = communityRepository.findById(id).get();
        CommunityDto communityDto = CommunityDto.builder()
                .id(community.getId())
                .userName(community.getUser().getUsername())
                .community_title(community.getCommunity_title())
                .uploadFile(community.getUploadFile())
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

    @Transactional
    public void updateLikeCounts(Long id, String userName) {
        Community community = communityRepository.findById(id).get();
        User user = userRepository.findByUsername(userName).get();
        Optional<LikeCheck> communityAndUser = likeCheckRepository.findByCommunityAndUser(community, user);
        communityAndUser.ifPresentOrElse(
                likeCheck -> {
                    likeCheckRepository.delete(likeCheck);
                    community.disCountLike(likeCheck);
                    community.updateLikeCount();
                }, () -> {
                    LikeCheck like = new LikeCheck();
                    like.mappingCommunity(community);
                    like.mappingUser(user);
                    community.updateLikeCount();
                    likeCheckRepository.save(like);
                }
        );
    }

    @Transactional
    public boolean likeCheck(Long id, String userName) {
        Community community = communityRepository.findById(id).get();
        User user = userRepository.findByUsername(userName).get();
        Optional<LikeCheck> communityAndUser = likeCheckRepository.findByCommunityAndUser(community, user);
        return communityAndUser.isPresent();
    }

    public boolean idCheck(Principal principal, String userName) {
        return principal.getName().equals(userName);
    }

    @Transactional
    public void deleteCommunity(Long id) {
        Community community = communityRepository.findById(id).get();
        if(community.getUploadFile() != null && !community.getUploadFile().getImageOriginal().isEmpty()) {
            imageService.deleteFile(community.getUploadFile().getImageOriginal());
        }
        communityRepository.deleteById(id);
    }



    public List<CommunityDto> getBest() {
        List<Community> communityList = communityRepository.findAll();
        List<Community> bestCommunityList = new ArrayList<>();
        CommunityDto communityDto = new CommunityDto();
        List<CommunityDto> bestCommunityDtoList = new ArrayList<>();
        if(!communityList.isEmpty() && communityList != null && communityList.size() >= 3) {
            for(int i = 0; i < 3; i++) {
                Integer max = -1;
                int item = 0;
                for(int j = 0; j < communityList.size(); j++) {
                    if(communityList.get(j).getLikeCounts() > max) {
                        max = communityList.get(j).getLikeCounts();
                        item = j;
                    }
                }
                bestCommunityList.add(communityList.get(item));
                communityList.remove(item);
            }
            bestCommunityDtoList = communityDto.toBestDtoList(bestCommunityList);
        }
        return bestCommunityDtoList;
    }
}

