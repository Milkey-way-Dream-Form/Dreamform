package com.milkyway.dreamform.controller;

import com.milkyway.dreamform.dto.ReplyRequestDto;
import com.milkyway.dreamform.model.Community;
import com.milkyway.dreamform.model.Reply;
import com.milkyway.dreamform.repository.CommunityRepository;
import com.milkyway.dreamform.repository.ReplyRepository;
import com.milkyway.dreamform.security.UserDetailsImpl;
import com.milkyway.dreamform.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    private final CommunityRepository communityRepository;
    private final ReplyRepository replyRepository;

    @GetMapping("/community/{communityId}/replies")
    public List<Reply> getReplyNo(@PathVariable Long communityId) {
        return replyRepository.findByCommunityId(communityId);
    }

    @PostMapping("/community/{communityId}/replies")
    public ResponseEntity saveComment(@PathVariable Long communityId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ReplyRequestDto replyRequestDto) {
        Community community = communityRepository.findById(communityId).orElse(null);
        if(community == null) {
            return ResponseEntity.badRequest().build();
        }
        replyService.saveReply(community, replyRequestDto, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/community/{communityId}/replies/{replyId}")
    public void updateReply(@PathVariable Long communityId, @PathVariable Long replyId,
                            @RequestBody String reply) throws Exception {
        replyService.updateReply(replyId, reply);
    }

    @DeleteMapping("/community/{communityId}/replies/{replyId}")
    public void deleteReply(@PathVariable Long communityId, @PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        replyService.deleteReply(communityId, replyId, userDetails.getUser().getId());
    }
}
