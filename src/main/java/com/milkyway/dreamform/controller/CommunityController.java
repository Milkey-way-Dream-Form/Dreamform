package com.milkyway.dreamform.controller;

import antlr.collections.List;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.security.Principal;

@Slf4j
@AllArgsConstructor
@Controller
public class CommunityController {
    CommunityService communityService;
    ImageService imageService;
    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, Model model, @RequestParam(defaultValue = "1") int page) {
        Page<Community> communityList = communityService.getCommunityList(pageable);
        Page<CommunityDto> communityDtoList = new CommunityDto().toDtoList(communityList);
        Pagenation pagenation = new Pagenation(communityDtoList, page);
        model.addAttribute("communities", communityDtoList);
        model.addAttribute("pagenation", pagenation);
        return "communityList";
    }

    @GetMapping("/create")
    public String createCommunityForm() {
        return "communityForm";
    }

    @PostMapping("/create")
    public String saveCommunity(Principal principal, @ModelAttribute CommunityDto communityDto) throws IOException {
        UploadFile image = imageService.saveFile(communityDto.getAttachFile());
        communityService.createCommunity(image ,principal.getName(), communityDto);
        return "redirect:/list";
    }

    @GetMapping("/community/{id}")
    public String detail(Principal principal, @PathVariable("id") Long id, Model model) {
        communityService.updateViewCounts(id);
        CommunityDto communityDto = communityService.getCommunity(id);
        boolean idCheck = communityService.idCheck(principal, communityDto.getUserName());
        model.addAttribute("community", communityDto);
        model.addAttribute("idCheck", idCheck);
        return "communityDetail";
    }

    @GetMapping("/community/editForm/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {
        CommunityDto communityDto = communityService.getCommunity(id);
        model.addAttribute("community", communityDto);
        return "communityEdit";
    }

    @PutMapping("/community/edit/{id}")
    public String editForm(@PathVariable("id") Long id, CommunityDto edit, RedirectAttributes redirectAttributes) throws IOException  {
        log.info("" + edit.getAttachFile().getOriginalFilename());
        CommunityDto communityDto = communityService.getCommunity(id);
        communityDto.setCommunity_title(edit.getCommunity_title());
        if(edit.getAttachFile() != null) {
            communityDto.setAttachFile(edit.getAttachFile());
            
        }
        communityDto.setCommunity_contents(edit.getCommunity_contents());
        communityService.createCommunity(communityDto.getUserName(), communityDto);
        redirectAttributes.addAttribute("id", communityDto.getCommunity_id());
        return "redirect:/community/{id}";
    }

    @DeleteMapping("/community/delete/{id}")
    public String deleteCommunity(@PathVariable("id") Long id) {
        communityService.deleteCommunity(id);
        return "redirect:/list";
    }
}
