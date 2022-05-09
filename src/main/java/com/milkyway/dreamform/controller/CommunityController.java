package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.service.CommunityService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@AllArgsConstructor
@Controller
public class CommunityController {
    CommunityService communityService;

    @GetMapping("/list")
    public String list(Model model) {


        return "";
    }
    @GetMapping("/create")
    public String create() {

        return "";
    }
}
