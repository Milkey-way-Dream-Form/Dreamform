package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.dto.CommunityRequestDto;
import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Slf4j
@Controller
public class CommunityController {
    @Autowired
    CommunityService communityService;

    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, Model model) {
        Page<Community> communityList = communityService.findCommunityList(pageable);
        communityList.stream().forEach(e -> e.getCommunity_contents());
        model.addAttribute("communitityList", communityList);
        return "communityList";
    }

    @GetMapping("/create")
    public String createCommunityForm() {
        return "communityForm";
    }

    @PostMapping("/create")
    public String createPost(Principal principal, CommunityRequestDto communityRequestDto) throws IOException {
        log.info(""+principal.getName());
        communityService.CreatePost(principal.getName(), communityRequestDto);
        return "redirect:/list";
    }
}
