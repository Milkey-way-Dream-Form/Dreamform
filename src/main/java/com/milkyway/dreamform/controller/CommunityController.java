package com.milkyway.dreamform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommunityController {
    @GetMapping("/list")
    public String list(Model model) {


        return "";
    }
    @GetMapping("/create")
    public String create() {

        return "";
    }
}
