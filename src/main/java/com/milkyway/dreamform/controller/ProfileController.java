package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("email",userDetails.getEmial());

        return "profile";
    }

}
