package com.milkyway.dreamform.controller;


import com.milkyway.dreamform.dto.CommunityDto;
import com.milkyway.dreamform.model.Community;

import com.milkyway.dreamform.model.Pagenation;
import com.milkyway.dreamform.model.UploadFile;
import com.milkyway.dreamform.service.CommunityService;
import com.milkyway.dreamform.service.ImageService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Controller
public class CommunityController {
    CommunityService communityService;
    ImageService imageService;
    @GetMapping("/list")
    public String list(Principal principal, @PageableDefault(size=10, sort="id", direction = Sort.Direction.DESC) Pageable pageable, Model model, @RequestParam(defaultValue = "1") int page) {
        Page<Community> communityList = communityService.getCommunityList(pageable);
        Page<CommunityDto> communityDtoList = new CommunityDto().toDtoList(communityList);
        Pagenation pagenation = new Pagenation(communityDtoList, page);
        model.addAttribute("username", principal.getName());
        model.addAttribute("communities", communityDtoList);
        model.addAttribute("pagenation", pagenation);
        return "communityList";
    }

    @GetMapping("/create")
    public String createCommunityForm(Principal principal, Model model) {
        model.addAttribute("username", principal.getName());
        return "communityForm";
    }

    @PostMapping("/create")
    public String saveCommunity(Principal principal, @ModelAttribute CommunityDto communityDto) throws IOException {
        UploadFile image = imageService.saveFile(communityDto.getAttachFile());
        communityService.createCommunity(image ,principal.getName(), communityDto);
        return "redirect:/list";
    }

    @GetMapping("/community/{id}")
    public String detail(Principal principal, @PathVariable("id") Long id, Model model) throws IOException {
        communityService.updateViewCounts(id);
        CommunityDto communityDto = communityService.getCommunity(id);
        boolean idCheck = communityService.idCheck(principal, communityDto.getUserName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("community", communityDto);
        model.addAttribute("idCheck", idCheck);
        model.addAttribute("likeCheck", communityService.likeCheck(id, principal.getName()));
        return "communityDetail";
    }

    @PostMapping("/likeUpdate/{communityId}")
    public String likeUpdate(@PathVariable("communityId") Long communityId, Principal principal, Model model) throws IOException {
        communityService.updateLikeCounts(communityId, principal.getName());
        CommunityDto communityDto = communityService.getCommunity(communityId);
        boolean idCheck = communityService.idCheck(principal, communityDto.getUserName());
        model.addAttribute("username", principal.getName());
        model.addAttribute("community", communityDto);
        model.addAttribute("idCheck", idCheck);
        model.addAttribute("likeCheck", communityService.likeCheck(communityId, principal.getName()));
        return "communityDetail";
    }

    @GetMapping("/community/editForm/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) throws IOException {
        CommunityDto communityDto = communityService.getCommunity(id);
        model.addAttribute("community", communityDto);
        return "communityEdit";
    }

    @PutMapping("/community/edit/{id}")
    public String editForm(@PathVariable("id") Long id, @ModelAttribute CommunityDto edit, RedirectAttributes redirectAttributes) throws IOException  {
        CommunityDto communityDto = communityService.getCommunity(id);
        if(communityDto.getUploadFile() != null) {
            if(!edit.getAttachFile().isEmpty()) {
                imageService.deleteFile(communityDto.getUploadFile().getImageOriginal());
                UploadFile image = imageService.saveFile(edit.getAttachFile());
                communityDto.setUploadFile(image);
                log.info("original is Not Null and edit not Null");
            }
        }else {
            if(!edit.getAttachFile().isEmpty()) {
                UploadFile image = imageService.saveFile(edit.getAttachFile());
                communityDto.setUploadFile(image);
                log.info("original is Null and edit not Null");
            }
        }
        communityDto.setCommunity_title(edit.getCommunity_title());
        communityDto.setCommunity_contents(edit.getCommunity_contents());
        communityService.createCommunity(communityDto.getUploadFile() ,communityDto.getUserName(), communityDto);
        redirectAttributes.addAttribute("id", communityDto.getId());
        return "redirect:/community/{id}";
    }

    @DeleteMapping("/community/delete/{id}")
    public String deleteCommunity(@PathVariable("id") Long id) {
        communityService.deleteCommunity(id);
        return "redirect:/list";
    }
}
