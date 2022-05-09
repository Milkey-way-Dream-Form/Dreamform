package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.dto.CommunityDto;
import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.service.CommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;

@Slf4j
@AllArgsConstructor
@Controller
public class CommunityController {
    CommunityService communityService;

    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, Model model) {
        Page<Community> communityList = communityService.getCommunityList(pageable);
        Page<CommunityDto> communityDtoList = new CommunityDto().toDtoList(communityList);
        model.addAttribute("communities", communityDtoList);
        return "communityList";
    }

    @GetMapping("/create")
    public String createCommunityForm() {
        return "communityForm";
    }

    @PostMapping("/create")
    public String createPost(Principal principal, @ModelAttribute CommunityDto communityDto) throws IOException {
        communityService.createCommunity(principal.getName(), communityDto);
        return "redirect:/list";
    }

    @GetMapping("/community/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        communityService.updateViewCounts(id);
        CommunityDto communityDto = communityService.getCommunity(id);
        model.addAttribute("community", communityDto);
        return "communityDetail";
    }
}
