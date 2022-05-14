package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.dto.ReplyRequestDto;
import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.Reply;
import com.milkyway.dreamform.repository.CommunityRepository;
import com.milkyway.dreamform.security.UserDetailsImpl;
import com.milkyway.dreamform.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final CommunityRepository communityRepository;

    @GetMapping("/community/{communityId}/replies/new")
    public String createForm(@PathVariable Long communityId, Model model) {
        model.addAttribute("form", new ReplyRequestDto());
        return "replies/createReplyForm";
    }

    @PostMapping("/community/{communityId}/replies/new")
    public String create(@PathVariable Long communityId, @AuthenticationPrincipal UserDetailsImpl userDetails, ReplyRequestDto replyRequestDto) {
        Community community = communityRepository.findById(communityId).orElse(null);
        replyService.saveReply(community, replyRequestDto, userDetails.getUser());
        return "redirect:/community/{communityId}/replies";
    }

    @GetMapping("/community/{communityId}/replies")
    public String list(@PathVariable Long communityId, Model model) {
        List<Reply> replies = replyService.findReplies();
        model.addAttribute("replies", replies);
        return "replies/replyList";
    }

    @GetMapping("/community/{communityId}/replies/{replyId}")
    public String updateReplyForm(@PathVariable Long communityId, @PathVariable Long replyId, Model model) {
        ReplyRequestDto replyRequestDto = new ReplyRequestDto();
        Reply reply = new Reply();
        replyRequestDto.setComment(reply.getComment());

        model.addAttribute("form", reply);
        return "replies/updateReplyForm";
    }

    @PostMapping("/community/{communityId}/replies/{replyId}")
    public String updateReply(@PathVariable Long communityId, @PathVariable Long replyId, @ModelAttribute("form") ReplyRequestDto replyRequestDto) {
        String comment = replyRequestDto.getComment();
        replyService.updateReply(replyId, comment);
        return "redirect:/community/{communityId}/replies";
    }

    @GetMapping("/community/{communityId}/replies/{replyId}/delete")
    public String deleteReplyForm(@PathVariable Long communityId, @PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        replyService.deleteReply(replyId, communityId, userDetails.getUser().getId());
        return "redirect:/community/{communityId}/replies";
    }
}
