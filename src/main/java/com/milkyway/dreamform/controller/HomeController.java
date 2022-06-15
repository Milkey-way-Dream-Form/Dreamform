package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.dto.CommunityDto;
import com.milkyway.dreamform.model.User;
import com.milkyway.dreamform.security.UserDetailsImpl;
import com.milkyway.dreamform.service.CommunityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class HomeController {
    CommunityService communityService;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        List<CommunityDto> bestCommunityListDto = communityService.getBest();
//        for(int i = 0; i < bestCommunityListDto.size(); i++) {
//            if(bestCommunityListDto.get(i).getUploadFile() == null) {
//                log.info("파일 유무 체크 : null임");
//            }else {
//                log.info("파일 유무 체크 : null아님");
//            }
//        }
        model.addAttribute("bestCommunityList", bestCommunityListDto);
        model.addAttribute("username", userDetails.getUsername());
        return "main";
    }
}
