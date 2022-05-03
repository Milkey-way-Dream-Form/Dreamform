package com.milkyway.dreamform.service;

import com.milkyway.dreamform.dto.CommunityRequestDto;
import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.repository.CommunityRepository;
import com.milkyway.dreamform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<Community> findCommunityList(Pageable pageable) {
        pageable = PageRequest.of(
                pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber()-1,
                pageable.getPageSize());
        return communityRepository.findAll(pageable);
    }

    public Community findCommunityById(Long id) {
        return communityRepository.findById(id).orElse(new Community());
    }

    @Transactional
    public Long CreatePost(String userName, CommunityRequestDto communityRequestDto) throws IOException {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("해당 아이디가 존재하지 않습니다. " + userName)
        );
        return communityRepository.save(communityRequestDto.toEntity(user)).getCommunity_id();
    }
}