package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.dto.CommunityDto;
import com.milkyway.dreamform.service.CommunityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
public class CommunityController {
    @Autowired
    CommunityService communityService;

    @GetMapping("/list")
    public String list(Model model) {
        List<CommunityDto> communityList = communityService.getCommunityList();
        model.addAttribute("communities", communityList);
        return "communityList";
    }

    @GetMapping("/create")
    public String createCommunityForm() {
        return "communityForm";
    }

    @PostMapping("/create")
    public String createPost(Principal principal, @ModelAttribute CommunityDto communityDto) throws IOException {
        log.info(""+principal.getName());
        communityService.CreatePost(principal.getName(), communityDto);
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
