package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.dto.CommunityDto;
import com.milkyway.dreamform.security.UserDetailsImpl;
import com.milkyway.dreamform.service.CommunityService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class HomeController {
    CommunityService communityService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<CommunityDto> bestCommunityListDto = communityService.getBest();
        model.addAttribute("bestCommunityList", bestCommunityListDto);
        model.addAttribute("username", userDetails.getUsername());
        return "index";
    }
}
