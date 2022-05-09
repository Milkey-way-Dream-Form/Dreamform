package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.service.APIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WorknetAPIController {
//
    private final APIService apiService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @PostMapping("/api")
    public String apiSave() {
         try {
             List<Map<String, String>> list = apiService.apiParsing();
             apiService.jobListSave(list);
         }catch (Exception e){
             log.info("" + e);
         }
         return "redirect:/test";
    }
}
